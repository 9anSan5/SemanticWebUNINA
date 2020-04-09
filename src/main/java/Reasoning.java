import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.elk.owlapi.ElkReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


import java.io.File;

public class Reasoning {

    public static void main(String[] args) throws OWLOntologyCreationException {

        precomputeInferencesELK();

        precomputeInferencesHermiT();
    }


    public static void precomputeInferencesELK() throws OWLOntologyCreationException {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology ont = man.loadOntologyFromOntologyDocument(new File("pizza.owl.xml"));

        // Create an ELK reasoner.
        ElkReasonerFactory reasonerFactory = new ElkReasonerFactory();
        OWLReasoner reasoner = reasonerFactory.createReasoner(ont);

        // Classify the ontology.
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);

        // Terminate the worker threads used by the reasoner.
        reasoner.dispose();
    }



    public static void precomputeInferencesHermiT() throws OWLOntologyCreationException {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology ont = man.loadOntologyFromOntologyDocument(new File("pizza.owl.xml"));


        Reasoner.ReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
        OWLReasoner reasoner = reasonerFactory.createReasoner(ont);
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);

        OWLDataFactory df = man.getOWLDataFactory();

        reasoner.getSubClasses(df.getOWLClass(
                "http://www.co−ode.org/ontologies/pizza/pizza.owl#RealItalianPizza"),
                false).
                forEach(System.out::println);;
    }
}
