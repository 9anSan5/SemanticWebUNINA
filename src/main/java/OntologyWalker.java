import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;

import java.util.Collections;

public class OntologyWalker {

    public static void main(String[] args) throws OWLOntologyCreationException {


        // How to walk the asserted structure of an ontology
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        IRI pizzaontology = IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
        OWLOntology o = man.loadOntology(pizzaontology);

        // Create the walker
        OWLOntologyWalker walker =
                new OWLOntologyWalker(Collections.singleton(o));
        // Now ask our walker to walk over the ontology
        OWLOntologyWalkerVisitor visitor =
                new OWLOntologyWalkerVisitor(walker) {
                    @Override
                    public void visit(OWLObjectSomeValuesFrom desc) {
                        System.out.println(desc);
                        System.out.println(" " + getCurrentAxiom() + "\n");
                    }
                };

        // Have the walker walk...
        walker.walkStructure(visitor);
    }

}