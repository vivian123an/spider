package I000.spider.football.pipeline;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import I000.common.StaticFinalString;
import I000.spider.football.dao.FootballMatchDao;
import I000.spider.football.model.FootballMatch;
import I000.spider.football.model.JzSfpMatchList;

@Component("JzSfpDaoPipeline")
public class JzSfpDaoPipeline implements PageModelPipeline<JzSfpMatchList> {
	@Resource
    private FootballMatchDao footballMatchDao;
	public FootballMatchDao getFootballMatchDao() {
		return footballMatchDao;
	}
	public void setFootballMatchDao(FootballMatchDao footballMatchDao) {
		this.footballMatchDao = footballMatchDao;
	}

	@Override
    public void process(JzSfpMatchList footballMatchList, Task task) {
        List<String> zidList = footballMatchList.getZidList();
        List<String> pdateList = footballMatchList.getPdateList();
        List<String> gameNumList = footballMatchList.getGameNumList();
        List<String> leagueList = footballMatchList.getLeagueList();
        List<String> endTimeList = footballMatchList.getEndTimeList();
        List<String> concedeTLineGreenList = footballMatchList.getConcedeTLineGreenList();
        List<String> homesxnameList = footballMatchList.getHomesxnameList();
        List<String> awaysxnameList = footballMatchList.getAwaysxnameList();
        List<String> nspfList = footballMatchList.getNspfList();
        List<String> spfList = footballMatchList.getSpfList();
        
        if(zidList!=null&&zidList.size()>0){
        	System.out.println("------------JzSfpMatchList:"+footballMatchList);
            for(int i=0;i<zidList.size();i++){
            	try {
            		String zid = zidList.get(i);
                	if(zid!=null&&!"".equals(zid.trim())){
                		FootballMatch footballMatchOld = 
                				footballMatchDao.getFootballMatchByZid(zid.trim(),StaticFinalString.FOOTBALL_JCZQ_SFP_CODE);
                    	if(footballMatchOld==null){
                    		FootballMatch footballMatch = new FootballMatch();
                    		
                    		footballMatch.setZid(zidList.get(i).trim());
                    		footballMatch.setTypeCode(StaticFinalString.FOOTBALL_JCZQ_SFP_CODE);
                    		footballMatch.setTypeName(StaticFinalString.FOOTBALL_JCZQ_SFP_NAME);
                    		footballMatch.setPdate(pdateList.get(i));
                    		//避免数组越界、临时处理方案
                    		if(i>gameNumList.size()-1||
                    				i>awaysxnameList.size()-1||
                    				i*3+2>nspfList.size()-1||
                    				i*3+2>spfList.size()-1)
                    			break;
                    		
                    		footballMatch.setGameNum(gameNumList.get(i));
                    		footballMatch.setLeague(leagueList.get(i));
                    		footballMatch.setEndTime(endTimeList.get(i));
                    		footballMatch.setConcedeTLineGreen(concedeTLineGreenList.get(i));
                    		footballMatch.setAwaysxname(awaysxnameList.get(i));
                    		footballMatch.setHomesxname(homesxnameList.get(i));
                    		
                    		if(!isValidData(nspfList,spfList,i))
                    			continue;
                    		
                    		footballMatch.setNspf1(nspfList.get(i*3));
                    		footballMatch.setNspf2(nspfList.get(i*3+1));
                    		footballMatch.setNspf3(nspfList.get(i*3+2));
                    		footballMatch.setSpf1(spfList.get(i*3));
                    		footballMatch.setSpf2(spfList.get(i*3+1));
                    		footballMatch.setSpf3(spfList.get(i*3+2));
                    		footballMatch.setCreateDate(new Date());
                    		footballMatch.setUpdateDate(new Date());
                    		
                    		footballMatchDao.save(footballMatch);
    						
                    	}else{
                    		footballMatchOld.setZid(zidList.get(i).trim());
                    		footballMatchOld.setTypeCode(StaticFinalString.FOOTBALL_JCZQ_SFP_CODE);
                    		footballMatchOld.setTypeName(StaticFinalString.FOOTBALL_JCZQ_SFP_NAME);
                    		footballMatchOld.setPdate(pdateList.get(i));
                    		//避免数组越界、临时处理方案
                    		if(i>gameNumList.size()-1||
                    				i>awaysxnameList.size()-1||
                    				i*3+2>nspfList.size()-1||
                    				i*3+2>spfList.size()-1)
                    			break;
                    		footballMatchOld.setGameNum(gameNumList.get(i));
                    		footballMatchOld.setLeague(leagueList.get(i));
                    		footballMatchOld.setEndTime(endTimeList.get(i));
                    		footballMatchOld.setAwaysxname(awaysxnameList.get(i));
                    		footballMatchOld.setHomesxname(homesxnameList.get(i));
                    		
                    		if(!isValidData(nspfList,spfList,i))
                    			continue;
                    			
                    		footballMatchOld.setNspf1(nspfList.get(i*3));
                    		footballMatchOld.setNspf2(nspfList.get(i*3+1));
                    		footballMatchOld.setNspf3(nspfList.get(i*3+2));
                    		footballMatchOld.setSpf1(spfList.get(i*3));
                    		footballMatchOld.setSpf2(spfList.get(i*3+1));
                    		footballMatchOld.setSpf3(spfList.get(i*3+2));
                    		footballMatchOld.setUpdateDate(new Date());
                    		
                    		footballMatchDao.update(footballMatchOld);
                    	}
                	}
            	} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
    }
	
	//判断数据是否有意义、
	//不为空、并且格式是为小数
	private boolean isValidData(List<String> nspfList,List<String> spfList,int i){
		Pattern pattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");//判断是否为小数
		if(StringUtil.isBlank(nspfList.get(i*3))||
				StringUtil.isBlank(nspfList.get(i*3+1))||
				StringUtil.isBlank(nspfList.get(i*3+2))||
				StringUtil.isBlank(spfList.get(i*3))||
				StringUtil.isBlank(spfList.get(i*3)+1)||
				StringUtil.isBlank(spfList.get(i*3)+2))
			return false;
		//判断是否为小数
		if(!pattern.matcher(nspfList.get(i*3)).matches()||
				!pattern.matcher(nspfList.get(i*3+1)).matches()||
				!pattern.matcher(nspfList.get(i*3+2)).matches()||
				!pattern.matcher(spfList.get(i*3)).matches()||
				!pattern.matcher(spfList.get(i*3+1)).matches()||
				!pattern.matcher(spfList.get(i*3+2)).matches())	
			return false;
		return true;
	}
}
