#设计文档

##项目需求
> 从我们网站的访问日志(access log)中，分析出单页访问的来源(http请求的referer的地址分类)

##功能分析
> 根据访问日志数据，统计单页访问的来源

##输入输出格式
> * 输入数据：输入数据为本网站的访问日志(access log)，包含了http head文件中相关信息。详细格式定义信息参考`src/main/java/com.anjuke.corp.ods.afs/Const.java`
> * 输出数据：输出数据为统计日志(result log)，格式为网页类别，相应数目。
     * 例：（站内网页  123)

##功能流程
> 1. 从access log中获取URL、referer、useragent参数
> 2. 正则表达式匹配URL为 `/prop/view*` 的URL,网站为 `anjuke.com` 的域名，筛选出满足条件的日志记录
> 3. 根据referer对统计访问来源
     * 爬虫等(useragent内包含spider、bot等关键字的日志记录)
     * 站内网页(referer域名`anjuke.com`、`haozu.com`、`jinpu.com`的日志记录)
     * 站外网页(referer为不包含`anjuke.com`、`haozu.com`、`jinpu.com`域名，为其它正常网页的日志记录)
     * 其他(不包含在以上3类，只统计数量，暂不处理）
