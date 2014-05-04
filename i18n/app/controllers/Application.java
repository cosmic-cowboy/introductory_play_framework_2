package controllers;

import play.*;
import play.i18n.Lang;
import play.i18n.Messages;
import play.mvc.*;

import views.html.*;
import views.html.index;

public class Application extends Controller {
  
    public static Result index() {

        // ブラウザの利用言語
        System.out.println("request().acceptLanguages()" + request().acceptLanguages());

        // 現在のHTTPコンテキストにおける言語
        System.out.println("Lang.defaultLang() : " + Lang.defaultLang());
        
        // The application languagesに指定してある言語
        System.out.println("Lang.availables() : " + Lang.availables());

        // 言語コードjaのLangオブジェクト取得
        System.out.println("Lang.get() : " + Lang.get("ja"));

        // 日本語での表示
        String title = Messages.get("welcome.to.play");
        String message = Messages.get("play.version", "2.2.2");
        System.out.println(title);
        System.out.println(message);

        // 言語コードenのLangオブジェクト取得
        Lang en = Lang.forCode("en");
        System.out.println(en.language());
        System.out.println(en.toLocale().getDisplayLanguage());

        // 英語での表示
        String titleEn = Messages.get(en, "welcome.to.play");
        String messageEn = Messages.get(en, "play.version", "2.2.2");
        System.out.println(titleEn);
        System.out.println(messageEn);

        return ok(index.render("Your new application is ready."));
    }
  
}
