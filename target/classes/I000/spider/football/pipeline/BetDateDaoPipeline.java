package I000.spider.football.pipeline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import I000.spider.football.dao.BetDateDao;
import I000.spider.football.model.BetDate;
import I000.spider.football.model.BetDateList;

@Component("BetDateDaoPipeline")
public class BetDateDaoPipeline implements PageModelPipeline<BetDateList> {

	@Resource
    private BetDateDao betDateDao;

    public BetDateDao getBetDateDao() {
		return betDateDao;
	}

	public void setBetDateDao(BetDateDao betDateDao) {
		this.betDateDao = betDateDao;
	}


	@Override
    public void process(BetDateList betDateList, Task task) {
        System.out.println("BetDateDaoPipeline:"+betDateList);
        
        List<String> dayDateList = betDateList.getDayDateList();
        List<String> matchInfoList = betDateList.getMatchInfoList();
        if(dayDateList!=null&&dayDateList.size()>0){
            for(int i=0;i<dayDateList.size();i++){
            	try {
            		String dayDate = dayDateList.get(i);
            		String pdate = getPdate(dayDate.trim());
                	String matchInfo = matchInfoList.get(i);
                	BetDate betDateOld = betDateDao.getBetDateByDayDate(dayDate.trim());
                	if(betDateOld==null){
                		BetDate betDate = new BetDate();
                		betDate.setDayDate(dayDate.trim());
                		betDate.setMatchInfo(matchInfo.trim());
                		betDate.setPdate(pdate);
                		betDate.setStatus(dateCompare(pdate));
                		betDate.setCreateDate(new Date());
                		betDate.setUpdateDate(new Date());
                		betDateDao.save(betDate);
                	}else{
                		betDateOld.setDayDate(dayDate.trim());
                		betDateOld.setMatchInfo(matchInfo.trim());
                		betDateOld.setStatus(dateCompare(pdate));
                		betDateOld.setUpdateDate(new Date());
                		betDateDao.update(betDateOld);
                	}
				} catch (Exception e) {
					e.printStackTrace();
				}
            	
            }
        }
    }
	
	private String getPdate(String dateString){
		if(dateString!=null){
			if(dateString.length()>=4)
				return dateString.substring(4);
		}
		return "";
	}
	
	/**
	 * 根据比赛的时间判断是否历史数据(历史数据返回0)
	 * @param dateStr
	 * @return
	 */
	private int dateCompare(String dateStr){
		Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date yesterday = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date sourceDate = sdf.parse(dateStr);
			if(sourceDate.after(yesterday)){
				return 1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
