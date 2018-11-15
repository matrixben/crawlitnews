package jason.luo.service.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import jason.luo.service.NewsService;

public class HuxiuFactory implements CrawlController.WebCrawlerFactory<HuxiuNews>{
    private NewsService newsService;
    public HuxiuFactory(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public HuxiuNews newInstance() {
        return new HuxiuNews(this.newsService);
    }
}
