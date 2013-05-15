#设计文档

##项目需求
> 从我们网站的访问日志(access log)中，分析出单页访问的来源(http请求的referer的地址分类)

##功能分析
> 根据访问日志数据，统计单页访问的来源

##输入输出格式
> * 输入数据：输入数据为本网站的访问日志(access log)，格式为TAB隔开各项参数的字符串。
> * 输出数据：输出数据为统计日志(result log)，格式为网页类别，相应数目。

##功能流程
> 1. 从access log中获取URL及referer参数
> 2. 正则表达式匹配URL为 `/prop/view*` 的URL,网站为 `anjuke.com` 的域名，筛选出满足条件的日志记录
> 3. 根据referer对统计访问来源
     * 爬虫等(bot/spider)
     * 站内网页(referer同为`anjuke.com`等网站)
     * 站外网页(referer为其他网页)
     * 其他
