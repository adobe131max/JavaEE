# Spring Cache

Spring Cache 是 Spring 框架提供的一个用于缓存数据的模块，它可以帮助开发者轻松地在应用程序中实现缓存功能，从而提高应用程序的性能和响应时间。Spring Cache 提供了一种声明式的方式来配置和使用缓存，使得开发者可以专注于业务逻辑而不必关心底层的缓存实现细节。

## 使用

- `@EnableCaching`  Application开启缓存
- `@CachePut`       将方法返回值放入缓存    value: 缓存名称，每个缓存名称下可以有多个key    key: 缓存的key
- `@CacheEvict`     清理指定缓存
- `@Cacheable`      在方法执行前spring先查看是否有数据，如果有数据，直接返回缓存数据，没有则执行方法，并把返回值加入缓存

默认不会持久化，服务只要重启就会清空所有缓存

`value`指定空间、`key`键、`allEntries = true`   清空对应空间中的缓存

`@Cacheable(value = "supplier", key = "#id")`
`@Cacheable(value = "supplier", key = "'list'")`
`@CacheEvict(value = "supplier", allEntries = true)`
