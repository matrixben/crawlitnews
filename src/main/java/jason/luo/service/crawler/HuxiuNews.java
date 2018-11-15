package jason.luo.service.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import jason.luo.service.NewsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.TimeZone;

/**
 * www.huxiu.com
 */
public class HuxiuNews extends WebCrawler {
    private NewsService newsService;

    HuxiuNews(NewsService newsService) {
        this.newsService = newsService;
    }

    public void visit(Page page) {
        int timeOffset = TimeZone.getTimeZone("Asia/Shanghai").getRawOffset()
                - TimeZone.getTimeZone("GMT").getRawOffset();
        String url = page.getWebURL().getURL();

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            Document doc = Jsoup.parse(html);

            Elements articles = doc.getElementsByClass("mod-art");
            for (int i = articles.size()-1; i >= 0; i--) {
                String title = articles.get(i).select("h2").text();
                String subUrl = articles.get(i).select("h2 > a").attr("href");
                Elements tags = articles.get(i).getElementsByClass("column-link-box");
                String tag = "虎嗅";  //tag可能为空或有多个
                if (!"".equals(tags.text().trim())){
                    tag = tags.get(0).child(0).text();
                }
                String fullUrlStr = url + subUrl.substring(1);

                Date today = new Date(new Date().getTime() + timeOffset);
                newsService.saveNews(title, tag, today, fullUrlStr);
//                newsService.printNews(title, tag, today, fullUrlStr);
            }


        }
    }
}
