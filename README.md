# ZUA NEWS

## 数据获取
爬取zua新闻网内容数据，并导入数据库中:  
```py
import requests
import re
import pymysql
from lxml import etree
from bs4 import BeautifulSoup
from datetime import datetime


class NewsSpider:
    def __init__(self):
        self.url = 'https://news.zua.edu.cn/xwtt1.htm'
        self.headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36'
        }
        self.total_pages = 1
        self.seen_titles = set()


        self.db_config = {
            'host': 'localhost',
            'user': 'root',
            'password': '',
            'database': '',
            'charset': 'utf8mb4'
        }

        self.conn = None
        self.cursor = None

    def clean_text(self, text):
        """清理文本中的特殊字符和空白"""
        if not text:
            return ''
        text = text.replace('\u200b', '').replace('\n', ' ').strip()
        return ' '.join(text.split())

    def connect_db(self):
        """连接MySQL数据库"""
        try:
            self.conn = pymysql.connect(**self.db_config)
            self.cursor = self.conn.cursor()
            print("成功连接到MySQL数据库")
        except pymysql.Error as e:
            print(f"数据库连接失败: {e}")
            raise

    def close_db(self):
        """关闭数据库连接"""
        if self.cursor:
            self.cursor.close()
        if self.conn:
            self.conn.close()
        print("数据库连接已关闭")

    def create_table(self):
        """创建新闻表"""
        sql = """
        CREATE TABLE IF NOT EXISTS news (
            id INT AUTO_INCREMENT PRIMARY KEY,
            title VARCHAR(255) NOT NULL,
            content TEXT,
            publish_date DATE,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            UNIQUE KEY (title)  -- 防止重复标题
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
        """
        try:
            self.cursor.execute(sql)
            self.conn.commit()
            print("新闻表创建成功或已存在")
        except pymysql.Error as e:
            print(f"创建表失败: {e}")
            self.conn.rollback()
            raise

    def insert_news(self, title, content, publish_date):
        """插入新闻数据到数据库"""
        sql = """
        INSERT INTO news (title, content, publish_date)
        VALUES (%s, %s, %s)
        ON DUPLICATE KEY UPDATE content=VALUES(content), publish_date=VALUES(publish_date)
        """
        try:
            self.cursor.execute(sql, (title, content, publish_date))
            self.conn.commit()
            return True
        except pymysql.Error as e:
            print(f"插入数据失败: {e}")
            self.conn.rollback()
            return False

    def get_total_pages(self, html):

        page_info = html.xpath('//*[@id="fanye126103"]/text()')
        if page_info:
            match = re.search(r"(\d+)/(\d+)", page_info[0])
            if match:
                return int(match.group(2))
        return 1

    def parse_news_page(self, html_content):

        soup = BeautifulSoup(html_content, 'html.parser')
        news_items = soup.find_all('li', class_='clearfix')
        news_data = []

        for item in news_items:
            title = self.clean_text(item.find('a').text)
            if title in self.seen_titles:
                continue

            self.seen_titles.add(title)
            content = self.clean_text(item.find('p').text)
            date_str = self.clean_text(item.find('div', class_='time').text)

            # 转换日期格式为YYYY-MM-DD
            try:
                publish_date = datetime.strptime(date_str, '%Y.%m.%d').date()
            except ValueError:
                publish_date = None

            news_data.append((title, content, publish_date))

        return news_data

    def fetch_page(self, url):

        try:
            response = requests.get(url, headers=self.headers)
            response.encoding = response.apparent_encoding
            response.raise_for_status()
            return response.text
        except requests.RequestException as e:
            print(f"请求页面失败: {e}")
            return None

    def run(self):

        try:

            self.connect_db()
            self.create_table()


            print("开始爬取首页...")
            html_content = self.fetch_page(self.url)
            if not html_content:
                return

            html = etree.HTML(html_content)
            self.total_pages = self.get_total_pages(html)

            news_data = self.parse_news_page(html_content)
            for title, content, publish_date in news_data:
                self.insert_news(title, content, publish_date)


            for page in range(1, self.total_pages):
                page_url = f'https://news.zua.edu.cn/xwtt1/{self.total_pages - page}.htm'
                print(f"正在爬取第 {page + 1} 页: {page_url}")

                page_content = self.fetch_page(page_url)
                if not page_content:
                    continue

                news_data = self.parse_news_page(page_content)
                for title, content, publish_date in news_data:
                    self.insert_news(title, content, publish_date)

            print("爬取完成！")

        except Exception as e:
            print(f"运行过程中发生错误: {e}")
        finally:
            self.close_db()


if __name__ == '__main__':
    spider = NewsSpider()
    spider.run()
```

## 前端 vue-front

代码在vue-front目录下

运行:
```sh
cd vue-front  
pnpm i   
pnpm dev
```

项目结构参考: [v3-admin-vite](https://github.com/un-pany/v3-admin-vite)

## 后端　　　
springboot3   mysql  mybatis  
基于 IDEA　Qoder ai插件完善编写  具体如下:   
1.你阅读整个项目理解下，并将项目按照config,controller,entity,entity下dto,vo;service,service下impl的项目结构，优化项目代码规范，不需要的目录只创建就行，不要硬写。  
2.将config，放到创建common公共类中   
3.你觉得未来这个新闻后台管理系统common目录，会写些什么模块，不用给代码  
4.这些模块如何在其他层调用，举例说明  
5.那按照上述完善common层  
6.我测试了下后端接口，感觉没问题，但前端有很多问题，我将前端代码放到的vue-shixi下，先理解分析。D:\springboot\demo\vue-shixi，就在该项目demo下  
7.按照上述帮我修改  
8.Solve the error:
```
com.example.xfk.common.exception.BusinessException: 未登录或登录已过期
	at com.example.xfk.common.interceptor.AuthInterceptor.preHandle(AuthInterceptor.java:26) ~[classes/:na]
	at org.springframework.web.servlet.HandlerExecutionChain.applyPreHandle(HandlerExecutionChain.java:146) ~[spring-webmvc-6.2.7.jar:6.2.7]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1084) ~[spring-webmvc-6.2.7.jar:6.2.7]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:979) ~[spring-webmvc-6.2.7.jar:6.2.7]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014) ~[spring-webmvc-6.2.7.jar:6.2.7]
	at org.springframework.web.servlet.FrameworkServlet.doOptions(FrameworkServlet.java:950) ~[spring-webmvc-6.2.7.jar:6.2.7]
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:599) ~[tomcat-embed-core-10.1.41.jar:6.0]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885) ~[spring-webmvc-6.2.7.jar:6.2.7]
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658) ~[tomcat-embed-core-10.1.41.jar:6.0]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:195) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51) ~[tomcat-embed-websocket-10.1.41.jar:10.1.41]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-6.2.7.jar:6.2.7]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.2.7.jar:6.2.7]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-6.2.7.jar:6.2.7]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.2.7.jar:6.2.7]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-6.2.7.jar:6.2.7]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.2.7.jar:6.2.7]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:483) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:116) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:344) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:398) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:903) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1740) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1189) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:658) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:63) ~[tomcat-embed-core-10.1.41.jar:10.1.41]
	at java.base/java.lang.Thread.run(Thread.java:1583) ~[na:na]
```
