//package com.jlife.abon.controller;
//
//import com.jlife.abon.controller.application.BaseController;
//import com.jlife.abon.dto.ArticleData;
//import com.jlife.abon.repository.ArticleRepository;
//import com.jlife.abon.service.MediaService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.IOException;
//
///**
// * @author Khralovich Dzmitry
// */
//@Controller
//public class BlogController extends BaseController {
//
//    private final static Logger logger = LoggerFactory.getLogger(BlogController.class);
//
//    public static final int PAGE_SIZE = 5;
//
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    @Autowired
//    private MediaService mediaService;
//
//    @RequestMapping(value = "/blog")
//    public String getArticles(ModelMap model) {
//
//        model.put("articles", articleRepository.findAll());
//        return "blog";
//    }
//
//    @RequestMapping(value = "/blog/{articleId}")
//    public String getArticle(@PathVariable("articleId") String articleId, ModelMap model) {
//
//        model.put("article", articleRepository.findOne(articleId));
//        return "article";
//    }
//
//    @RequestMapping(value = "/system/blog")
//    public String getSystemArticles(ModelMap model) {
//
//        model.put("articles", articleRepository.findAll());
//        return "system-blog";
//    }
//
//    @RequestMapping(value = "/system/blog/{articleId}")
//    public String getSystemArticle(@PathVariable("articleId") String articleId, ModelMap model) {
//        if("new".equals(articleId)){
//            model.put("article", new ArticleData());
//        } else {
//            model.put("article", articleRepository.findOne(articleId));
//        }
//        return "system-article";
//    }
//
//    @RequestMapping(value = "/system/blog/article/save",
//            method = RequestMethod.POST)
//    public String saveEvent(@ModelAttribute("article") ArticleData article,
//                            @RequestParam(value = "logoFile", required = false) MultipartFile logoFile,
//                            RedirectAttributes rAttrs) {
//        if (logoFile != null) {
//            if (logoFile.getSize() != 0) {
//                try {
//                    String imagePath = mediaService.saveImage(logoFile, 800, 600);
//                    article.setLogo(imagePath);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        String msg;
//        if (article.getId() == null) {
//            articleRepository.save(article);
//            msg = "Статья сохранена!";
//        } else {
//            ArticleData oldArticle = articleRepository.findOne(article.getId());
//            oldArticle.merge(article);
//            articleRepository.save(oldArticle);
//            msg = "Статья добавлена!";
//        }
//        rAttrs.addFlashAttribute("flashMessage", msg);
//        return "redirect:/system/blog/" + article.getId();
//    }
//}
