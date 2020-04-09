import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;

import java.io.File;
import java.io.FileNotFoundException;


public class OWLAPIFirst {

    public static void main(String[] args) throws OWLOntologyCreationException,
            FileNotFoundException, OWLOntologyStorageException {

        loadOntologyFromFile("pizza.owl.xml");

        loadOntologyFromWeb("http://protege.stanford.edu/ontologies/pizza/pizza.owl");

    }




    //Load an ontology directly from your local file
    public static void loadOntologyFromFile(String fileName)
            throws OWLOntologyCreationException {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        File file = new File(fileName);
        OWLOntology o = man.loadOntologyFromOntologyDocument(file);
        System.out.println(o);
    }




    //Directly load an ontology from the web
    public static void loadOntologyFromWeb(String iri)
            throws OWLOntologyCreationException {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        IRI pizzaontology = IRI.create(iri);
        OWLOntology o = man.loadOntology(pizzaontology);
        System.out.println(o);
    }


}
