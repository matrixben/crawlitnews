package jason.luo.dao;

import jason.luo.domain.News;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsDao extends CrudRepository<News, Integer> {
    List<News> findNewsByTitleLikeOrderByPublishDateDesc(String title);
}
