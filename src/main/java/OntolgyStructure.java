import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLObjectTransformer;
import org.semanticweb.owlapi.vocab.OWLFacet;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;


/*
    Traversing the ontology
 */

public class OntolgyStructure {


    public static void main(String[] args) throws OWLOntologyCreationException,
            FileNotFoundException, OWLOntologyStorageException {


        namedClasses("http://protege.stanford.edu/ontologies/pizza/pizza.owl");

        assertedSuperClasses("http://protege.stanford.edu/ontologies/pizza/pizza.owl",
                "#Napoletana");


        addAxioms();

        changeAxioms();

    }



    // Asserted Superclasses

    public static void assertedSuperClasses(String ontologyIRI, String classIRI)
            throws OWLOntologyCreationException {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        IRI pizzaontology = IRI.create(ontologyIRI);
        OWLOntology o = man.loadOntology(pizzaontology);
        OWLDataFactory df = man.getOWLDataFactory();

        OWLClass cls = df.getOWLClass(IRI.create(pizzaontology + classIRI));
        // for each superclass there will be a corresponding axiom
        // the ontology indexes axioms in a variety of ways
        Set<OWLSubClassOfAxiom> sameSuperClasses = o
                .getSubClassAxiomsForSubClass(cls);

        Stream<OWLSubClassOfAxiom> superClasses = o.subClassAxiomsForSuperClass(cls);
        System.out.println(superClasses.count() == sameSuperClasses.size());

    }



    // Named classes referenced by axioms in the ontology.

    public static void namedClasses(String ontologyIRI)
            throws OWLOntologyCreationException {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        IRI pizzaontology = IRI.create(ontologyIRI);
        OWLOntology o = man.loadOntology(pizzaontology);

        for (OWLClass cls : o.getClassesInSignature())  //DEPRECATED
            System.out.println(cls);

        o.classesInSignature().forEach(System.out::println);


        /* Iterate through all OWLEntity’s in the signature (that
        do not belong to the built-in vocabulary),
        and for each of them check whether their name starts with a “P”.
        */
        o.signature().filter((e -> (!e.isBuiltIn() && e.getIRI().getFragment().startsWith("P")))).
                forEach(System.out::println);  //DEPRECATED


        // The IRI Remainder as a Java Optional
        o.signature().filter(e -> !e.isBuiltIn() &&
                e.getIRI().getRemainder().orElse("").startsWith("P")).
                forEach(System.out::println);
    }



    // Add axioms to the ontology

    public static void addAxioms() throws OWLOntologyCreationException {

        IRI IOR = IRI.create("http://owl.api.tutorial");
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology o = man.createOntology(IOR);
        OWLDataFactory df = man.getOWLDataFactory();

        OWLClass person = df.getOWLClass(IOR + "#Person");
        OWLDeclarationAxiom da = df.getOWLDeclarationAxiom(person);

        // Add the class declaration axiom to the ontology
        o.add(da);

        //or
        // man.addAxiom(o, da)

        //or
        // AddAxiom ax = new AddAxiom(o, da);
        // man.applyChange(ax);

        System.out.println(o);


        // Add SubCLassOf axiom
        OWLClass woman = df.getOWLClass(IOR + "#Woman");
        OWLSubClassOfAxiom w_sub_p = df.getOWLSubClassOfAxiom(woman, person);
        o.add(w_sub_p);


        // ObjectProperty Restriction

        // We do this by creating an existential (some) restriction
        OWLObjectProperty hasPart = df.getOWLObjectProperty(
                IRI.create(IOR + "#hasPart"));
        OWLClass nose = df.getOWLClass(
                IRI.create(IOR + "#Nose"));

        // Now let’s describe the class of individuals that have at
        // least one part that is a kind of nose
        OWLClassExpression hasPartSomeNose =
                df.getOWLObjectSomeValuesFrom(hasPart, nose);
        OWLClass head =
                df.getOWLClass(IRI.create(IOR + "#Head"));

        // Head subclass of our restriction
        OWLSubClassOfAxiom ax =
                df.getOWLSubClassOfAxiom(head, hasPartSomeNose);
        man.applyChange(new AddAxiom(o, ax));


        //Datatype Restriction

        // Adults have an age greater than 18.
        OWLDataProperty hasAge = df.getOWLDataProperty(
                IRI.create(IOR + "#hasAge"));
        // Create the restricted data range
        OWLDataRange greaterThan18 = df.getOWLDatatypeRestriction(
                df.getIntegerOWLDatatype(), OWLFacet.MIN_INCLUSIVE,
                df.getOWLLiteral(18));
        // Now we can use this in our datatype restriction on hasAge
        OWLClassExpression adultDefinition =
                df.getOWLDataSomeValuesFrom(hasAge, greaterThan18);
        OWLClass adult = df.getOWLClass(IRI.create(
                IOR + "#Adult"));
        OWLSubClassOfAxiom ax2 =
                df.getOWLSubClassOfAxiom(adult, adultDefinition);
        man.applyChange(new AddAxiom(o, ax2));

    }




    // Replace sub-expressions in axioms

    public static void changeAxioms() throws OWLOntologyCreationException {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        IRI IOR = IRI.create("http://owl.api.tutorial");
        OWLOntology o = man.loadOntology(IOR);
        OWLDataFactory df = man.getOWLDataFactory();

        final Map<OWLClassExpression, OWLClassExpression> replacements = new HashMap<>();

        OWLClass A = df.getOWLClass(IOR + "#A");
        OWLClass B = df.getOWLClass(IOR + "#B");
        OWLClass X = df.getOWLClass(IOR + "#X");
        OWLObjectProperty R = df.getOWLObjectProperty(IOR + "#R");
        OWLObjectProperty S = df.getOWLObjectProperty(IOR + "#S");
        OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(
                df.getOWLObjectSomeValuesFrom(R, A),
                df.getOWLObjectSomeValuesFrom(S, B));
        o.add(ax);
        
        // Print all logical axioms
        o.logicalAxioms().forEach(System.out::println);


        replacements.put(df.getOWLObjectSomeValuesFrom(R, A), X);

        OWLObjectTransformer<OWLClassExpression> replacer =
                new OWLObjectTransformer<>((x) -> true, (input) -> {
                    OWLClassExpression l = replacements.get(input);
                    if (l == null) {
                        return input;
                    }
                    return l;
                }, df, OWLClassExpression.class);

        List<OWLOntologyChange> results = replacer.change(o);
        o.applyChanges(results);
    }

}
