package App;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import App.Rivista.Periodicita;

public class CatalogoBibliotecario {
    private List<ElementoCatalogo> archivio;

    public CatalogoBibliotecario() {
        archivio = new ArrayList<>();
    }

    public void aggiungiElemento(ElementoCatalogo elemento) {
        archivio.add(elemento);
    }

    public void rimuoviElemento(String isbn) {
        archivio.removeIf(elemento -> elemento.getIsbn().equals(isbn));
    }

    public ElementoCatalogo ricercaPerIsbn(String isbn) {
        return archivio.stream()
                .filter(elemento -> elemento.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<ElementoCatalogo> ricercaPerAnnoPubblicazione(int anno) {
        return archivio.stream()
                .filter(elemento -> elemento.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());
    }

    public List<ElementoCatalogo> ricercaPerAutore(String autore) {
        List<ElementoCatalogo> risultato = new ArrayList<>();
        for (ElementoCatalogo elemento : archivio) {
            if (elemento instanceof Libro && ((Libro) elemento).getAutore().equals(autore)) {
                risultato.add(elemento);
            }
        }
        return risultato;
    }

    public void salvataggioSuDisco(String nomeFile) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFile))) {
            oos.writeObject(archivio);
            System.out.println("Archivio salvato correttamente su disco.");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio su disco: " + e.getMessage());
        }
    }

    public void caricamentoDalDisco(String nomeFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFile))) {
            archivio = (List<ElementoCatalogo>) ois.readObject();
            System.out.println("Archivio caricato correttamente dal disco.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Errore durante il caricamento dal disco: " + e.getMessage());
        }
    }
    
    public void stampaCatalogo() {
        for (ElementoCatalogo elemento : archivio) {
            System.out.println(elemento);
        }
    }
    
    public void salvaCatalogo(String nomeFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile))) {
            for (ElementoCatalogo elemento : archivio) {
                writer.write(elemento.toString());
                writer.newLine();
            }
            System.out.println("Catalogo salvato correttamente!");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio del catalogo: " + e.getMessage());
        }
    }
    
    public void caricaCatalogo(String nomeFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";"); // Separazione dei valori tramite il carattere ";"
                if (values.length >= 5) {
                    String codiceISBN = values[0];
                    String titolo = values[1];
                    int annoPubblicazione = Integer.parseInt(values[2]);
                    int numeroPagine = Integer.parseInt(values[3]);

                    String tipoElemento = values[4];
                    if (tipoElemento.equals("LIBRO")) {
                        if (values.length >= 7) {
                            String autore = values[5];
                            String genere = values[6];
                            Libro libro = new Libro(codiceISBN, titolo, annoPubblicazione, numeroPagine, autore, genere);
                            aggiungiElemento(libro);
                        }
                    } else if (tipoElemento.equals("RIVISTA")) {
                        if (values.length >= 6) {
                            String period = values[5];
                            Rivista rivista = new Rivista(codiceISBN, titolo, annoPubblicazione, numeroPagine, period);
                            aggiungiElemento(rivista);
                        }
                    }
                }
            }
            System.out.println("Catalogo caricato correttamente!");
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento del catalogo: " + e.getMessage());
        }
    }

}
