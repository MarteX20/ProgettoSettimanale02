package App;

import java.util.List;

import App.Rivista.Periodicita;

public class Main {
    public static void main(String[] args) {
        // Creazione del catalogo bibliotecario
        CatalogoBibliotecario catalogo = new CatalogoBibliotecario();

        // Aggiunta di elementi al catalogo
        catalogo.aggiungiElemento(new Libro("1234567890", "Il nome della rosa", 1980, 500, "Umberto Eco", "Romanzo"));
        catalogo.aggiungiElemento(new Rivista("9876543210", "National Geographic", 2021, 100, Periodicita.MENSILE));

        // Stampa del catalogo
        System.out.println("Catalogo bibliotecario:");
        catalogo.stampaCatalogo();

        // Ricerca per ISBN
        ElementoCatalogo elemento1 = catalogo.ricercaPerIsbn("1234567890");
        if (elemento1 != null) {
            System.out.println("Elemento trovato per ISBN: " + elemento1);
        } else {
            System.out.println("Nessun elemento trovato per ISBN");
        }

        // Ricerca per anno pubblicazione
        List<ElementoCatalogo> elementi2 = catalogo.ricercaPerAnnoPubblicazione(2021);
        if (!elementi2.isEmpty()) {
            System.out.println("Elementi trovati per anno pubblicazione:");
            for (ElementoCatalogo elemento : elementi2) {
                System.out.println(elemento);
            }
        } else {
            System.out.println("Nessun elemento trovato per anno pubblicazione");
        }

        // Ricerca per autore
        List<ElementoCatalogo> elementi3 = catalogo.ricercaPerAutore("Umberto Eco");
        if (!elementi3.isEmpty()) {
            System.out.println("Elementi trovati per autore:");
            for (ElementoCatalogo elemento : elementi3) {
                System.out.println(elemento);
            }
        } else {
            System.out.println("Nessun elemento trovato per autore");
        }

        // Rimozione di un elemento
        catalogo.rimuoviElemento("9876543210");

        // Stampa del catalogo aggiornato
        System.out.println("Catalogo bibliotecario aggiornato:");
        catalogo.stampaCatalogo();

        // Salvataggio su disco dell'archivio
        String fileName = "catalogo.txt";
        catalogo.salvaCatalogo(fileName);
        System.out.println("Catalogo salvato su disco");

        // Caricamento dal disco dell'archivio
        CatalogoBibliotecario catalogoSalvato = CatalogoBibliotecario(fileName);
        if (catalogoSalvato != null) {
            System.out.println("Catalogo caricato da disco:");
            catalogoSalvato.stampaCatalogo();
        } else {
            System.out.println("Errore durante il caricamento del catalogo da disco");
        }
    }
}
