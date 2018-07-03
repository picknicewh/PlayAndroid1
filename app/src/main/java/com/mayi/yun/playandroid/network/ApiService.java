package com.mayi.yun.playandroid.network;

import com.mayi.yun.playandroid.bean.Article;
import com.mayi.yun.playandroid.bean.Banner;
import com.mayi.yun.playandroid.bean.Friend;
import com.mayi.yun.playandroid.bean.HotKey;
import com.mayi.yun.playandroid.bean.KnowledgeSystem;
import com.mayi.yun.playandroid.bean.Result;
import com.mayi.yun.playandroid.bean.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 作者： wh
 * 时间：  2018/2/27
 * 名称：网络请求地址
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface ApiService {
    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     *
     * @param page page页码
     */
    @GET("/article/list/{page}/json")
    Observable<Result<Article>> getHomeArticles(@Path("page") int page);

    /**
     * 首页Banner
     *
     * @return BannerResponse
     */
    @GET("/banner/json")
    Observable<Result<List<Banner>>> getHomeBanners();

    /**
     * 获取知识体系
     * http://www.wanandroid.com/tree/json
     */
    @GET("/tree/json")
    Observable<Result<List<KnowledgeSystem>>> getKnowledgeSystem();

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0/json?cid=168
     *
     * @param page page
     * @param cid  cid
     */
    @GET("/article/list/{page}/json")
    Observable<Result<Article>> getKnowledgeSystemArticles(@Path("page") int page, @Query("cid") int cid);


    /**
     * 常用网站
     * http://www.wanandroid.com/friend/json
     */
    @GET("/friend/json")
    Observable<Result<List<Friend>>> getHotFriends();

    /**
     * 大家都在搜
     * http://www.wanandroid.com/hotkey/json
     */
    @GET("/hotkey/json")
    Observable<Result<List<HotKey>>> getHotKeys();

    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     *
     * @param page page
     * @param k    POST search key
     */
    @POST("/article/query/{page}/json")
    @FormUrlEncoded
    Observable<Result<Article>> getSearchArticles(@Path("page") int page, @Field("k") String k);


    /**
     * 登录
     *
     * @param username username
     * @param password password
     * @return Deferred<User>
     */
    @POST("/user/login")
    @FormUrlEncoded
    Observable<Result<User>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     *
     * @param username   username
     * @param password   password
     * @param repassword repassword
     * @return Deferred<User>
     */
    @POST("/user/register")
    @FormUrlEncoded
    Observable<Result<User>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 收藏文章
     *
     * @param id id
     * @return Deferred<DataResponse>
     */
    @POST("/lg/collect/{id}/json")
    Observable<Result> addCollectArticle(@Path("id") int id);

    /**
     * 收藏站外文章
     *
     * @param title  title
     * @param author author
     * @param link   link
     * @return Deferred<DataResponse>
     */
    @POST("/lg/collect/add/json")
    @FormUrlEncoded
    Observable<Result> addCollectOutsideArticle(@Field("title") String title, @Field("author") String author, @Field("link") String link);

    /**
     * 删除收藏文章
     *
     * @param id       id
     * @param originId -1
     * @return Deferred<DataResponse>
     */
    @POST("/lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<Result> removeCollectArticle(@Path("id") int id, @Field("originId") int originId);


    /**
     * 获取自己收藏的文章列表
     *
     * @param page page
     * @return Deferred<Article>
     */
    @GET("/lg/collect/list/{page}/json")
    Observable<Result<Article>> getCollectArticles(@Path("page") int page);

    /**
     * 我的书签
     * http://www.wanandroid.com/lg/collect/usertools/json
     */
    @GET("/lg/collect/usertools/json")
    Observable<Result<List<Friend>>> getBookmarks();

    /**
     * 编辑书签
     * http://www.wanandroid.com/lg/collect/updatetool/json
     */
    @POST("/lg/collect/usertools/json")
    @FormUrlEncoded
    Observable<Result> editBookmark(@Field("id") int id, @Field("name") String name, @Field("link") String link);

    /**
     * 删除书签
     * http://www.wanandroid.com/lg/collect/deletetool/json
     */
    @POST("/lg/collect/usertools/json")
    @FormUrlEncoded
    Observable<Result> delBookmark(@Field("id") int id);
}
