package main

import (
	"flag"
	"fmt"
	"log"

	"github.com/lab1/pkg/helpers/xml"
	"github.com/lab1/pkg/types"
)

const (
	Obfuscate   = "obfuscate"
	Deobfuscate = "deobfuscate"
)

var (
	mod        = flag.String("mod", "deobfuscate", fmt.Sprintf("Accepted value: %s, %s", Obfuscate, Deobfuscate))
	inputFile  = flag.String("input-file", "assets/result.xml", "Name of file with input data")
	outputFile = flag.String("output-file", "assets/deobuf.xml", "Name of file with output data")
)

func main() {
	flag.Parse()
	empl := &types.Employees{}
	if *mod == Obfuscate {
		err := xml.UnmarshalFileToStruct(*inputFile, empl)
		if err != nil {
			panic(err)
		}
		empl.Obfuscate()
		err = xml.MarshalStructToFile(*outputFile, empl)
		if err != nil {
			panic(err)
		}
	} else if *mod == Deobfuscate {
		err := xml.UnmarshalFileToStruct(*inputFile, empl)
		if err != nil {
			panic(err)
		}
		empl.Deobfuscate()
		err = xml.MarshalStructToFile(*outputFile, empl)
		if err != nil {
			panic(err)
		}
	} else {
		log.Fatalf("Invalid argument for mod: %v", *mod)
	}
}
