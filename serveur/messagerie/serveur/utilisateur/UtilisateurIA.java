package messagerie.serveur.utilisateur;

import messagerie.serveur.Session;
import messagerie.serveur.discussion.Discussion;
import messagerie.serveur.discussion.DiscussionTexte;
import messagerie.serveur.discussion.Message;
import messagerie.serveur.exceptions.DiscussionException;


import java.util.Scanner;
import java.util.*;
import java.lang.Math;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
* Classe représentant une intelligence artificielle.
*/
public class UtilisateurIA extends Utilisateur {
	public final static long serialVersionUID = 7894L;
	private Map<String, ArrayList<String>> voc = new HashMap<>();
	private static ArrayList<String> categorieSalutation = new ArrayList<String>();
	private static ArrayList<String> feeling = new ArrayList<String>();
	private Random randomGenerator = new Random();

	/**
	* Création d'un utilisateur.
	* @param pseudonyme Pseudonyme de l'utilisateur.
	* @param nom Nom de l'utilisateur.
	* @param prenom Prénom de l'utilisateur.
	*/
	public UtilisateurIA(String pseudonyme, String nom, String prenom) {
		super(pseudonyme, nom, prenom);
		this.setAllChainesXML("ressources/UserQuestion/salutationUser.xml", categorieSalutation, false);
		this.setAllChainesXML("ressources/UserQuestion/feelingUser.xml", feeling, false);
		this.setAllChainesXML("ressources/MiaReponse/salutationMia.xml", categorieSalutation, true);
		this.setAllChainesXML("ressources/MiaReponse/feelingMia.xml", feeling, true);
	}

	public void setCategorie(String str, ArrayList<String> categorie) {
		categorie.add(str);
	}

	public void setVoc(String str, ArrayList<String> categorie) {
		this.voc.put(str,categorie);
	}

	public void setAllChainesXML(String nomFichier, ArrayList<String> categorie, Boolean chaineMia) {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document= builder.parse(new File(nomFichier));
			final Element racine = document.getDocumentElement();

			final NodeList expressions = racine.getElementsByTagName("expression");
			final int nbExpressionElements = expressions.getLength();

			for(int j = 0; j<nbExpressionElements; j++) {
				final Element expression = (Element) expressions.item(j);
				String tmp = expression.getTextContent();
				if(chaineMia) {
					setCategorie(tmp,categorie);
				} else {
					this.setVoc(tmp,categorie);
				}

			}

		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (final SAXException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	* Répondre à un message.
	* @param message Message auquel doit répondre l'intelligence artificielle.
	* @return Réponse au message.
	*/
	public Message repondre(Message message) throws DiscussionException, Exception {
		long id = message.getId();
		Discussion discussion = Session.getApplication().getDiscussion(id);
		if (discussion instanceof DiscussionTexte) {
			String msgUser = message.getMessage();
			msgUser = msgUser.toLowerCase();
			ArrayList<String> selectionRep = this.voc.get(msgUser);
			if (selectionRep == null) {
				return new Message(this,"Désolé, je n'ai pas compris..",(DiscussionTexte)discussion);
			} else {
				int index = randomGenerator.nextInt(selectionRep.size());
				String repMia = selectionRep.get(index);
				return new Message(this,repMia,(DiscussionTexte)discussion);
			}
		} else {
			throw new DiscussionException("Type de discussion incompatible");
		}
	}
}
