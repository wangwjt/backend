这是公司一个门禁黑名单组件，技术栈是

springmvc4+swagger2+mybatis+spring-ldap+postgresql分区表+activemq+redis+logback+csv文件导出



建库脚本见  event.sql



而且本文展示了如何使用

spring-ldap

以及如何正确处理文件下载文件名乱码问题(需要区分火狐以及非火狐浏览器).



值得指出的是, 测试的时候读取的配置文件就是 src/test/resources 目录下面的配置文件

而跑工程的时候读取的配置文件就是 src/main/resources 目录下面的配置文件

而且通过这个项目，我知道了ajax请求文件下载的时候，ajax并不能真正下载文件，而只能采用新增dom提交表单或者直接window.location.href跳转到下载action去两种方法.
