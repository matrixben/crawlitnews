package jason.luo.service;

import jason.luo.dao.NewsDao;
import jason.luo.domain.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 存入数据库的操作方法
 */
public class NewsService {
    private static final Logger log = LoggerFactory.getLogger(NewsService.class);

    @Autowired
    private NewsDao newsDao;

    public void saveNews(String title, String tag, Date publishDate, String fullUrlStr) {
        News news = new News();
        news.setTitle(title);
        news.setTag(tag);
        news.setPublishDate(publishDate);
        news.setSourceUrl(fullUrlStr);

        if (!isNewsExist(title)) {
            newsDao.save(news);
        }else {
            log.info(news.getTitle().substring(0, 6) + "... is already in.");
        }
    }

    private boolean isNewsExist(String title) {
        if ("".equals(title) || title == null){
            return false;
        }
        List<News> l = newsDao.findNewsByTitleLikeOrderByPublishDateDesc(title);

        return (!l.isEmpty() && l.get(0) != null && l.get(0).getTitle().equals(title));
    }

}
