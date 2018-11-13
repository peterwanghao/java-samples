package com.peterwanghao.samples.java.archaius;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**   
 * @ClassName:  ApplicationConfigTest
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date:   2018年11月13日 下午3:52:12
 * @version V1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/archaiusContext.xml" })
public class ApplicationConfigTest {
	@Autowired
	private ApplicationConfig appConfig;

	@Autowired
	private JdbcTemplate template;

	@Test
	public void shouldRetrieveThePropertyByKey() {
		String property = appConfig.getStringProperty("hello.world.message", "default message");

		assertThat(property, is("Hello Archaius World!"));
	}

	@Test
	public void shouldRetrieveDefaultValueWhenKeyIsNotPresent() {
		String property = appConfig.getStringProperty("some.key", "default message");

		assertThat(property, is("default message"));
	}

	@Test
	public void shouldReadCascadeConfigurationFiles() {
		String property = appConfig.getStringProperty("cascade.property", "not found");

		assertThat(property, is("cascade value"));
	}

	@Test
	public void shouldRetrievePropertyFromDB() {
		String property = appConfig.getStringProperty("db.property", "default message");

		assertThat(property, is("this is a db property"));
	}

	@Test
	public void shouldReadTheNewValueAfterTheSpecifiedDelay() throws InterruptedException, SQLException {
		template.update("update properties set property_value = 'changed value' where property_key = 'db.property'");

		String propertyValue = (String) template.queryForObject(
				"select property_value from properties where property_key = 'db.property'", java.lang.String.class);
		System.out.println(propertyValue);

		String property = appConfig.getStringProperty("db.property", "default message");

		// We updated the value on the DB but Archaius polls for changes every 10000
		// milliseconds so it still sees the old value
		assertThat(property, is("this is a db property"));

		Thread.sleep(30000);

		property = appConfig.getStringProperty("db.property", "default message");
		assertThat(property, is("changed value"));
	}
}
