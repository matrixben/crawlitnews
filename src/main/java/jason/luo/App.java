package jason.luo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 单独的爬虫spring-boot项目，使用crawler4j
 * 数据保存到postgreSQL
 */
@SpringBootApplication
@EnableScheduling
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
