package jason.luo.service.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import jason.luo.service.NewsService;

public class SolidotFactory implements CrawlController.WebCrawlerFactory<SolidotNews> {
    private NewsService newsService;

    public SolidotFactory(NewsService newsService){
        this.newsService = newsService;
    }
    @Override
    public SolidotNews newInstance() {
        return new SolidotNews(this.newsService);
    }
}
