package I000.spider.football;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class JzJqProcesser implements PageProcessor {

	private Site site = Site.me().setDomain("caipiao.163.com").addStartUrl("http://caipiao.163.com/order/jczq-jinqiu/");

	@Override
	public void process(Page page) {
		/*System.out.println("-------------------------");
		System.out.println(page.getHtml().xpath("//tr//@zid").all().size());
		for(String str:page.getHtml().xpath("//tr//@zid").all()){
			System.out.println(str);
		}*/
		
		System.out.println("-------------------------");
		/*System.out.println(page.getHtml().xpath("//i[@class='jtip']/text()").all().size());
		for(String str:page.getHtml().xpath("//i[@class='jtip']/text()").all()){
			System.out.println(str);
		}*/
		
		System.out.println(page.getHtml().xpath("//span[@class='co3']/i[@class='jtip']/text()").all().size());
		for(String str:page.getHtml().xpath("//span[@class='co3']/i[@class='jtip']/text()").all()){
			System.out.println(str);
		}
	}
	
	@Override
	public Site getSite() {
        return site;
	}
	
	public static void main(String[] args) {
        Spider.create(new JzJqProcesser()).pipeline(new ConsolePipeline()).run();
    }

}
