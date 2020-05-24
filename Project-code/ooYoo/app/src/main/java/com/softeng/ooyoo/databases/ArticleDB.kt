package com.softeng.ooyoo.databases

import com.softeng.ooyoo.Article

class ArticleDB: Database(ARTICLES) {

    public fun retrieveArticles(articleId: String): ArrayList<Article>{

        return arrayListOf()
    }

}