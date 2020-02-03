package com.hd.base.configuration;

import com.hd.base.controller.BaseController;
import com.hd.base.interceptor.CrossDomainFilter;
import com.hd.base.interceptor.TokenInterceptor;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

/**
 * 工程配置类
 *
 * @author JLF
 * Created in 2019/3/12 10:44
 */
@SpringBootConfiguration
@CrossOrigin
public class ProjectConfiguration {

    /**
     * 配置全局异常处理
     */
    @ControllerAdvice
    class XmkjErrorHandler {

        /**
         * 异常处理方法
         *
         * @param req  请求
         * @param resp 响应
         * @param e    异常
         * @return ModelAndView
         * @throws Exception 不可预估的异常
         */
        @ExceptionHandler(value = Exception.class)
        public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) throws Exception {

            ModelAndView mav = new ModelAndView();

            //如果非Ajax
            if (null == req.getHeader("x-requested-with")) {

                mav.setViewName("redirect:/errorPage");

            } else {

                mav.setViewName("redirect:/errorJson");
            }
            return mav;
        }

    }

    /**
     * 异常处理Controller
     */
    @Controller
    class XmkjErrorController extends BaseController {

        /**
         * 跳页异常
         *
         * @return 登录页
         */
        @RequestMapping("errorPage")
        public String errorPage() {

            return REDIRECT + "/";
        }

        /**
         * Ajax 异常
         *
         * @return 错误报文
         */
        @RequestMapping("errorJson")
        @ResponseBody
        public Map<String, Object> errorJson() {
            return setReturnMessageMap(FAILURE, SpringUtils.getMessage(MessageConstant.MSG_ERROR_COMMON_0007));
        }
    }

    /**
     * 拦截器配置
     */
    @Component
    class InterceptorConfig implements WebMvcConfigurer {

        /**
         * 校验拦截器
         */
        @Resource
        private TokenInterceptor tokenInterceptor;

        /**
         * 注册拦截器
         */
        @Override
        public void addInterceptors(InterceptorRegistry registry) {

            // 校验拦截器注册器
            InterceptorRegistration accessInterceptorRegistration = registry
                    .addInterceptor(tokenInterceptor);
            // 校验拦截/**
            accessInterceptorRegistration.addPathPatterns("/**");
            // 排除校验拦截的路径
            String[] accessExcludePatterns = {
                    "/login/toLogin",
                    "/login/doLogin",
                    "/app/login",
                    "/userLogin/checkLogin",
                    "/userLogin/**",
                    "/userLogin",
                    "/common/css/**",
                    "/common/images/**",
                    "/common/js/**",
                    "/manage/css/**",
                    "/manage/js/**",
                    "/errorPage",
                    "/errorJson",
                    "/**/**.js",
                    "/**/**.css",
                    "**/**.jpg",
                    "/**/**/**.jpg",
                    "/**/**/**.css.map",
                    "/**/**.js.map",
                    "/**/**.ttf",
                    "/**/**.woff",
                    "/**/**.png",
                    "/**/index.html",
                    "/commonModule/**",
                    "/**/downLoad/**",
                    "/**/preview/**",
                    "/preview/fileViewer/**",
                    "/preview/fileInputStreamViewer/**",
                    "/preview/fileInputStreamViewerNoSubmitPreview/**",
                    "/",

                    // TODO 临时的图片 DEMO 中临时使用
                    "/customImages/**",
                    "/MP_verify_EEHOxNSD8SzUuaPg.txt",

                    "/plugins/**"
            };
            // 设置排除校验拦截的路径
            accessInterceptorRegistration.excludePathPatterns(Arrays.asList(accessExcludePatterns));

            // 缓存拦截器
            WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
            //设置 缓存时间：0s
            webContentInterceptor.setCacheSeconds(0);

            // 缓存拦截器注册器
            InterceptorRegistration cacheInterceptorRegistration = registry
                    .addInterceptor(webContentInterceptor);
            // 缓存拦截
            cacheInterceptorRegistration.addPathPatterns("/**");
            // 排除缓存拦截的路径
            String[] cacheExcludePatterns = {
                    "/PC/**",
                    "/APP/**"
            };

            // 设置排除缓存拦截的路径
            cacheInterceptorRegistration.excludePathPatterns(Arrays.asList(cacheExcludePatterns));
        }
    }

    @Bean
    @SuppressWarnings("unchecked")
    public FilterRegistrationBean registerFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CrossDomainFilter());
        registration.addUrlPatterns("/*");
        registration.setName("CrossDomainFilter");
        registration.setOrder(1);
        return registration;
    }

}
