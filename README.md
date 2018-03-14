Jena with support for UCUM README
===========

This project adds a UCUM Datatype in [Apache Jena](https://jena.apache.org/), along with different utility SPARQL functions.

A valid UCUM literal consists of the concatenation of:

- a valid xsd:decimal
- a space
- a valid code for unit of measure as defined in http://unitsofmeasure.org/ucum.html

**Any unit that is valid for UCUM is valid for Jena-UCUM!**


Examples of valid UCUM literals:

```
@Prefix cdt: <http://w3id.org/lindt/custom_datatypes#>. 

"1 [ft_i]"^^cdt:ucum -- foot, International customary units

"1.0 [ft_br]"^^cdt:ucum -- foot, British Imperial lengths 

"10e-1 um"^^cdt:ucum -- micrometer

"-2.47e-4 L/(min.m2)"^^cdt:ucum -- liter per minute and square meter

"0.7 km/s"^^cdt:ucum --> kilometer per second

"10.54 %"^^cdt:ucum --> percents

"37.5 Cel"^^cdt:ucum --> degree Celsius

"2.45e-1 [in_i'Hg]"^^cdt:ucum , or equivalently, "2.45e-1 m[Hg].[in_i]/m"^^cdt:ucum --> inch of mercury column   

"1.5647e6 {rbc} "^^cdt:ucum -- red blood cell count
```

For the compete specification of UCUM code system: 

http://unitsofmeasure.org/ucum.html


## Available RDF Datatype

- http://w3id.org/lindt/custom_datatypes#ucum

## Available SPARQL function

- http://w3id.org/lindt/custom_datatypes#sameDimension

## Overloading native SPARQL operators in Apache Jena

You can use native SPARQL operators (`=`, `<`, etc.) to compare UCUM literals. 


## Instructions to use with Apache Jena

To use, follow these steps:

First, clone the repository and install project locally (requires Git and Maven)

```bash
git clone git@github.com:OpenSensingCity/jena-ucum.git
cd jena-ucum
mvn install
```

Then, add the following to your Maven project's `pom.xml`:

```xml
   <properties>
        <!-- the version of jena you are using (mini 3.x) -->
        <jena.version>3.6.0-ucum</jena.version> 
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.apache.jena</groupId>
            <artifactId>jena-arq</artifactId>
            <version>3.6.0-ucum</version>
        </dependency>
    </dependencies>
```

# Thanks!

- Gunther Schadow and Clement J. McDonald for their work on the Unified Code for Units of Measures - http://unitsofmeasure.org/ucum.html
- Werner Keilw and Magno N. A. Cruz for their implementation of UCUM on Java SE - https://github.com/unitsofmeasurement/uom-systems