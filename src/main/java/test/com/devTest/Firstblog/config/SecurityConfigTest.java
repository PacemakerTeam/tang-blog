package test.com.devTest.Firstblog.config; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** 
* SecurityConfig Tester. 
* 
* @author <Authors name> 
* @since <pre>5월 7, 2021</pre> 
* @version 1.0 
*/ 
public class SecurityConfigTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: encodePWD() 
* 
*/ 
@Test
public void testEncodePWD() throws Exception { 
    String encPassword = new BCryptPasswordEncoder().encode("1234");
    System.out.println("1234해쉬 : " + encPassword);
} 

/** 
* 
* Method: configure(HttpSecurity http) 
* 
*/ 
@Test
public void testConfigure() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: encode(CharSequence rawPassword) 
* 
*/ 
@Test
public void testEncode() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: matches(CharSequence rawPassword, String encodedPassword) 
* 
*/ 
@Test
public void testMatches() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: upgradeEncoding(String encodedPassword) 
* 
*/ 
@Test
public void testUpgradeEncoding() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: toString() 
* 
*/ 
@Test
public void testToString() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: inMemoryAuthentication() 
* 
*/ 
@Test
public void testInMemoryAuthentication() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: jdbcAuthentication() 
* 
*/ 
@Test
public void testJdbcAuthentication() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: userDetailsService(T userDetailsService) 
* 
*/ 
@Test
public void testUserDetailsService() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: authenticate(Authentication authentication) 
* 
*/ 
@Test
public void testAuthenticate() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: loadUserByUsername(String username) 
* 
*/ 
@Test
public void testLoadUserByUsername() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: getPasswordEncoder() 
* 
*/ 
@Test
public void testGetPasswordEncoder() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = SecurityConfig.getClass().getMethod("getPasswordEncoder"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: getBeanOrNull(Class<T> type) 
* 
*/ 
@Test
public void testGetBeanOrNull() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = SecurityConfig.getClass().getMethod("getBeanOrNull", Class<T>.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: getAuthenticationManagerBeanNames(ApplicationContext applicationContext) 
* 
*/ 
@Test
public void testGetAuthenticationManagerBeanNames() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = SecurityConfig.getClass().getMethod("getAuthenticationManagerBeanNames", ApplicationContext.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: validateBeanCycle(Object auth, Set<String> beanNames) 
* 
*/ 
@Test
public void testValidateBeanCycle() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = SecurityConfig.getClass().getMethod("validateBeanCycle", Object.class, Set<String>.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
