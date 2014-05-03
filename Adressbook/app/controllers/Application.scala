package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models._
import anorm._

object Application extends Controller {

	val form1 = Form(
		mapping(
			"id" -> ignored(NotAssigned:Pk[Long]),
			"name" -> text,
			"age"  -> number,
			"address" -> text,
			"tel" -> text
		)(AddressBook.apply)(AddressBook.unapply)
	);
	
	def index = Action {
		val book:List[AddressBook] = AddressBook.all;
		Ok(views.html.index("住所録一覧", book));
	}

	def add = Action { implicit requesst =>
  		val reform = form1.bindFromRequest;
  		Ok(views.html.add("住所録登録", reform));
	}

	def send = Action { implicit requesst =>
  		val reform = form1.bindFromRequest;
  		reform.get.add;
  		Redirect("/");
	}
}