package jason.luo.controller;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import jason.luo.service.crawler.SolidotNews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Scheduled(fixedRate = 5000)
    public void solidotCrawler(){
        int numberOfCrawlers = 1;
        String solidotURL = "https://www.solidot.org";
        try {
            CrawlController controller = initCrawlController();
            controller.addSeed(solidotURL);
            controller.startNonBlocking(SolidotNews.class, numberOfCrawlers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CrawlController initCrawlController() throws Exception {
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

        return new CrawlController(config, pageFetcher, robotstxtServer);
    }
}
