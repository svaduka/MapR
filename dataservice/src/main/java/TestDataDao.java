

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.ojai.Document;
import org.ojai.DocumentStream;
import org.ojai.store.Query;

public class TestDataDao 
{
	private static Map<String, String> finaldata = new HashMap<String, String>();
	public Set<String> getEligibilityData(int numberOfRecords,int limit,int type) throws IOException
	{
		String[] fields = {"_id","records[0].value.payload.aboNumber","records[0].value.payload.affiliateNumber","records[0].value.payload.type","records[0].value.payload.reason"};
		Query query=AmwayDBUtil.getConnection().newQuery().select(fields)
				.where("{\"$and\":[{\"$eq\":{\"records[0].value.payload.description\":\"Bronze Foundation\"}}"
//						+"{\"$or\":["
//						+ "{\"$eq\":{\"records[0].value.payload.type\":"+type+"}}]}"
								+ "]}")
				.limit(limit).build();
		DocumentStream stream = AmwayDBUtil.getAmwayDocumentStore("/amway/magic/bonus/services/award/db/final-tables/award-eligibility").find(query);
		Document doc = null;
		Set<String> data = new TreeSet<String>(new Comparator<String>() {

			@Override
			public int compare(String source, String destination) {
				 String tokens[] = source.split("|");
				 String desttokens[] = destination.split("|");
				 if(tokens[0].equalsIgnoreCase(desttokens[0])
						 && tokens[1].equalsIgnoreCase(desttokens[1])
						 && tokens[2].equalsIgnoreCase(desttokens[2]))
				 {
					 return 0;
				 }
				return 1;
			}
			
		});
		for (Document userDocument : stream) {
			doc = userDocument;
			int aff = doc.getInt("records[0].value.payload.affiliateNumber");
			long abo = doc.getLong("records[0].value.payload.aboNumber");
			int awardCode= doc.getInt("records[0].value.payload.type");
			String reason=doc.getString("records[0].value.payload.reason");
			
			String id = getEligibilityId(aff, abo, awardCode);
			boolean isExist = eligibilityExist(id);
			if(isExist) {
				data.add(id+"|"+reason);
				System.out.println(finaldata.size());	
			}
			if(data.size() == numberOfRecords) {
				break;
				
			}
			
		}
		return data;
		
	}
	
	public boolean eligibilityExist(final String id) {
		Document corePlusDoc = AmwayDBUtil.getConnection().getStore("/amway/magic/bonus/services/award/db/final-tables/award-eligibility").findById(id);
		if(corePlusDoc!=null) {
			return true;
		}
		return false;
	}
	
	public long getBonusPeriodByAff(int aff, final String affliateMasterTableName) throws IOException {
		Document affMasterDoc = AmwayDBUtil.getConnection().getStore(affliateMasterTableName)
				.findById(String.valueOf(aff));
		return (Long) affMasterDoc.getMap("data").get("CURR_BNS_PER_NO");

	}
	
	public String getEligibilityId(int aff,long abo,int awardCode) throws IOException {
		long bonusPeriod=getBonusPeriodByAff(aff, "/amway/magic/bonus/services/los/db/final-tables/WWSMGC01.WWT12010_AFF_MST");
		return abo+"|"+aff+"|"+awardCode+"|"+bonusPeriod;
	}
	
	public void printResults(Set<String> eligibilityIds) {
		for (String data: eligibilityIds) {
			System.out.println(data);
		}
	}
	public static void main(String[] args) throws IOException {
		TestDataDao dao = new TestDataDao();
		Set<String> data = dao.getEligibilityData(25,Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		if(data!=null && data.size()!=0) {
			dao.printResults(data);
		}
	}

}
