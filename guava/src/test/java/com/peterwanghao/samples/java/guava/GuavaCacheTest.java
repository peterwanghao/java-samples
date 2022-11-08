package com.peterwanghao.samples.java.guava;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;

public class GuavaCacheTest {

	@Test
	public void whenCacheMiss_thenValueIsComputed() {
		CacheLoader<String, String> loader;
		loader = new CacheLoader<String, String>() {
			@Override
			public String load(String key) {
				return key.toUpperCase();
			}
		};

		LoadingCache<String, String> cache;
		cache = CacheBuilder.newBuilder().build(loader);

		assertEquals(0, cache.size());
		assertEquals("HELLO", cache.getUnchecked("hello"));
		assertEquals(1, cache.size());
	}

	// Eviction by Size
	@Test
	public void whenCacheReachMaxSize_thenEviction() {
		CacheLoader<String, String> loader;
		loader = new CacheLoader<String, String>() {
			@Override
			public String load(String key) {
				return key.toUpperCase();
			}
		};
		LoadingCache<String, String> cache;
		cache = CacheBuilder.newBuilder().maximumSize(3).build(loader);

		cache.getUnchecked("first");
		cache.getUnchecked("second");
		cache.getUnchecked("third");
		cache.getUnchecked("forth");
		assertEquals(3, cache.size());
		assertNull(cache.getIfPresent("first"));
		assertEquals("FORTH", cache.getIfPresent("forth"));
	}

	// Eviction by Weight
	// 如果想限制缓存占据内存的大小可以配置maximumWeight参数
	// maximumWeight定义了所有cache value加起的weigher的总和不能超过的上限。
	@Test
	public void whenCacheReachMaxWeight_thenEviction() {
		CacheLoader<String, String> loader;
		loader = new CacheLoader<String, String>() {
			@Override
			public String load(String key) {
				return key.toUpperCase();
			}
		};

		Weigher<String, String> weighByLength;
		weighByLength = new Weigher<String, String>() {
			@Override
			public int weigh(String key, String value) {
				// System.out.println(key + ":" + value + ":" + value.length());
				return value.length();
			}
		};

		LoadingCache<String, String> cache;
		cache = CacheBuilder.newBuilder().maximumWeight(16).weigher(weighByLength).build(loader);

		cache.getUnchecked("first");
		cache.getUnchecked("second");
		cache.getUnchecked("third");
		cache.getUnchecked("forth");
		assertEquals(3, cache.size());
		assertNull(cache.getIfPresent("first"));
		assertEquals("FORTH", cache.getIfPresent("forth"));
	}

	// Eviction by Time
	// remove records that have been idle for 2ms
	@Test
	public void whenEntryIdle_thenEviction() throws InterruptedException {
		CacheLoader<String, String> loader;
		loader = new CacheLoader<String, String>() {
			@Override
			public String load(String key) {
				return key.toUpperCase();
			}
		};

		LoadingCache<String, String> cache;
		cache = CacheBuilder.newBuilder().expireAfterAccess(2, TimeUnit.MILLISECONDS).build(loader);

		cache.getUnchecked("hello");
		assertEquals(1, cache.size());

		cache.getUnchecked("hello");
		Thread.sleep(300);

		cache.getUnchecked("test");
		assertEquals(1, cache.size());
		assertNull(cache.getIfPresent("hello"));
	}

	// Eviction by Time
	// evict records based on their total live time
	@Test
	public void whenEntryLiveTimeExpire_thenEviction() throws InterruptedException {
		CacheLoader<String, String> loader;
		loader = new CacheLoader<String, String>() {
			@Override
			public String load(String key) {
				return key.toUpperCase();
			}
		};

		LoadingCache<String, String> cache;
		cache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MILLISECONDS).build(loader);

		cache.getUnchecked("hello");
		assertEquals(1, cache.size());
		Thread.sleep(300);
		cache.getUnchecked("test");
		assertEquals(1, cache.size());
		assertNull(cache.getIfPresent("hello"));
	}

	// Automatic Refresh
	@Test
	public void whenLiveTimeEnd_thenRefresh() {
		CacheLoader<String, String> loader;
		loader = new CacheLoader<String, String>() {
			@Override
			public String load(String key) {
				return key.toUpperCase();
			}
		};

		LoadingCache<String, String> cache;
		cache = CacheBuilder.newBuilder().refreshAfterWrite(1, TimeUnit.MINUTES).build(loader);
	}

	// we'll add multiple records into our cache using a Map
	@Test
	public void whenPreloadCache_thenUsePutAll() {
		CacheLoader<String, String> loader;
		loader = new CacheLoader<String, String>() {
			@Override
			public String load(String key) {
				return key.toUpperCase();
			}
		};

		LoadingCache<String, String> cache;
		cache = CacheBuilder.newBuilder().build(loader);

		Map<String, String> map = new HashMap<String, String>();
		map.put("first", "FIRST");
		map.put("second", "SECOND");
		cache.putAll(map);

		assertEquals(2, cache.size());
	}

	// RemovalNotification
	@Test
	public void whenEntryRemovedFromCache_thenNotify() {
		CacheLoader<String, String> loader;
		loader = new CacheLoader<String, String>() {
			@Override
			public String load(final String key) {
				return key.toUpperCase();
			}
		};

		RemovalListener<String, String> listener;
		listener = new RemovalListener<String, String>() {
			@Override
			public void onRemoval(RemovalNotification<String, String> n) {
				if (n.wasEvicted()) {
					String cause = n.getCause().name();
					assertEquals(RemovalCause.SIZE.toString(), cause);
				}
			}
		};

		LoadingCache<String, String> cache;
		cache = CacheBuilder.newBuilder().maximumSize(3).removalListener(listener).build(loader);

		cache.getUnchecked("first");
		cache.getUnchecked("second");
		cache.getUnchecked("third");
		cache.getUnchecked("last");
		assertEquals(3, cache.size());
	}
}
