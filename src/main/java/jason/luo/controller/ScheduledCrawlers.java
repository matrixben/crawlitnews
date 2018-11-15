package jason.luo.controller;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import jason.luo.service.NewsService;
import jason.luo.service.crawler.HuxiuFactory;
import jason.luo.service.crawler.SolidotFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;

/**
 * 调度类，控制所有爬虫的运行周期
 */
@Component
public class ScheduledCrawlers {
    private static final Logger log = LoggerFactory.getLogger(ScheduledCrawlers.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    @Autowired
    private NewsService newsService;

    @Scheduled(cron = "0 0 1/2 * * ? ")
    public void solidotCrawler(){
        int numberOfCrawlers = 1;
        String solidotURL = "https://www.solidot.org";
        CrawlController controller = initCrawlController();
        controller.addSeed(solidotURL);
        controller.startNonBlocking(new SolidotFactory(newsService), numberOfCrawlers);
    }

    @Scheduled(cron = "0 0 0/2 * * ? ")
    public void huxiuCrawler(){
        int numberOfCrawlers = 1;
        String huxiuURL = "https://www.huxiu.com";
        CrawlController controller = initCrawlController();
        controller.addSeed(huxiuURL);
        controller.startNonBlocking(new HuxiuFactory(newsService), numberOfCrawlers);
    }

    private CrawlController initCrawlController() {
        String crawlStorageFolder = ".";
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setPolitenessDelay(1000);
        config.setMaxDepthOfCrawling(0);
        config.setMaxPagesToFetch(2);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        try {
            return new CrawlController(config, pageFetcher, robotstxtServer);
        } catch (Exception e) {
            log.info("Initialize CrawlerController FAIL: " + e.getMessage());
        }
        return null;
    }

}
