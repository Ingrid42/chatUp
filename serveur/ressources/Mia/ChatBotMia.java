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


public class ChatBotMia {

	private Map<String, ArrayList> voc = new HashMap<>();
	private static ArrayList<String> categorieSalutation = new ArrayList<String>();
	private static ArrayList<String> feeling = new ArrayList<String>();
	private Random randomGenerator = new Random();


	public ChatBotMia(){}


	public void setCategorie(String str, ArrayList<String> categorie) {
		categorie.add(str);
	}

	public void setVoc(String str, ArrayList categorie) {
		this.voc.put(str,categorie);
	}

	public void discuter() {
		while(true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("--> Moi : ");
			String quote = sc.nextLine();
			quote = quote.toLowerCase();
			ArrayList selectionRep = this.voc.get(quote);
			if (selectionRep == null) {
				System.out.println("--> Mia : \n"+"Désolé, je n'ai pas compris.."+"\n \n");
			} else {
				int index = randomGenerator.nextInt(selectionRep.size());
				String repMia = (String)selectionRep.get(index);
				System.out.println("--> Mia : \n"+repMia+"\n \n");
			}
		}
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

	public static void main(String[] args){
		ChatBotMia mia = new ChatBotMia();

		/*********** Set chaine User pour reconnaissance ************/
		mia.setAllChainesXML("UserQuestion/salutationUser.xml", categorieSalutation, false);
		mia.setAllChainesXML("UserQuestion/feelingUser.xml", feeling, false);


		/*********** Set chaine de Mia pour réponse ************/
		mia.setAllChainesXML("MiaReponse/salutationMia.xml", categorieSalutation, true);
		mia.setAllChainesXML("MiaReponse/feelingMia.xml", feeling, true);


		System.out.println(" \n Salut ! Je m'appelle Mia, je suis ravie de discuter avec toi aujourd'hui ! \n");
		mia.discuter();
	}

}
