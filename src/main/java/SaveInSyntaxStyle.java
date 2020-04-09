import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;




public class SaveInSyntaxStyle {

    public static void main(String[] args) throws OWLOntologyCreationException,
            FileNotFoundException, OWLOntologyStorageException {

        saveInFSS("ont.func.owl", "http://owl.api.tutorial");

        saveOWLXMLSS("ont.owl.xml", "http://owl.api.tutorial");

        saveManchesterSS("ont.man.owl", "http://owl.api.tutorial");

        saveRDFXMLSS("ont.rdf.xml", "http://owl.api.tutorial");
    }


    //Functional Style Syntax

    static void saveInFSS(String file, String iri) throws OWLOntologyCreationException,
            FileNotFoundException, OWLOntologyStorageException {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();

        File fileout = new File(file);
        IRI IOR = IRI.create(iri);

        OWLOntology o = man.loadOntology(IOR);
        man.saveOntology(o, new FunctionalSyntaxDocumentFormat(),
                new FileOutputStream(fileout));

    }


    //OWL/XML Syntax

    static void saveOWLXMLSS(String file, String iri) throws OWLOntologyCreationException,
            FileNotFoundException, OWLOntologyStorageException {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();

        File fileout = new File(file);
        IRI IOR = IRI.create(iri);

        OWLOntology o = man.loadOntology(IOR);
        man.saveOntology(o, new OWLXMLDocumentFormat(),
                new FileOutputStream(fileout));
    }


    //Manchester Syntax

    static void saveManchesterSS(String file, String iri) throws OWLOntologyCreationException,
            FileNotFoundException, OWLOntologyStorageException {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();

        File fileout = new File(file);
        IRI IOR = IRI.create(iri);

        OWLOntology o = man.loadOntology(IOR);
        man.saveOntology(o, new ManchesterSyntaxDocumentFormat(),
                new FileOutputStream(fileout));
    }


    //RDF/XML Syntax

    static void saveRDFXMLSS(String file, String iri) throws OWLOntologyCreationException,
            FileNotFoundException, OWLOntologyStorageException {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();

        File fileout = new File(file);
        IRI IOR = IRI.create(iri);

        OWLOntology o = man.loadOntology(IOR);
        man.saveOntology(o, new RDFXMLDocumentFormat(),
                new FileOutputStream(fileout));
    }


}
