package jason.luo.service.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import jason.luo.service.NewsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.TimeZone;

/**
 * https://www.solidot.org
 */
public class SolidotNews extends WebCrawler {
    @Autowired
    private NewsService newsService;

    public boolean shouldVisit(Page refPage, WebURL url) {
        String urlStr = url.getURL().toLowerCase();
        return true;
    }

    public void visit(Page page) {
        int timeOffset = TimeZone.getTimeZone("Asia/Shanghai").getRawOffset()
                - TimeZone.getTimeZone("GMT").getRawOffset();
        String url = page.getWebURL().getURL();

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            Document doc = Jsoup.parse(html);
            Elements articles = doc.getElementsByClass("block_m");
            for (int i = articles.size()-1; i >= 0; i--) {
                String title = articles.get(i).select("h2").text();
                String subUrl = articles.get(i).select("h2 > a").attr("href");
                String tag = articles.get(i).getElementsByClass("icon_float").get(0).child(0).attr("title");
                String fullUrlStr = url + subUrl.substring(1);

                Date today = new Date(new Date().getTime() + timeOffset);
                newsService.saveNews(title, tag, today, fullUrlStr);
            }

        }
    }
}
