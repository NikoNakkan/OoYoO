package com.softeng.ooyoo.databases

import com.softeng.ooyoo.portfolio.Article

/**
 * This class provides communication with the database for anything related with articles
 */
class ArticleDB: Database(ARTICLES) {

    public fun retrieveArticles(articleId: String): ArrayList<Article>{
        TODO("Not yet implemented")
    }

}