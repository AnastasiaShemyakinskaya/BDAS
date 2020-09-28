package types

import (
	"encoding/xml"

	"github.com/lab1/pkg/helpers/obfuscator"
)

type Employees struct {
	XMLName  xml.Name   `xml:"employees"`
	Employee []Employee `xml:"employee"`
}

type Employee struct {
	Id         string   `xml:"id,attr"`
	XMLName    xml.Name `xml:"employee"`
	FirstName  string   `xml:"firstName"`
	SecondName string   `xml:"lastName"`
	Location   string   `xml:"location"`
}

func (e *Employees) Obfuscate() {
	for i, employee := range e.Employee {
		e.Employee[i].Id = obfuscator.Obfuscate(employee.Id)
		e.Employee[i].FirstName = obfuscator.Obfuscate(employee.FirstName)
		e.Employee[i].SecondName = obfuscator.Obfuscate(employee.SecondName)
		e.Employee[i].Location = obfuscator.Obfuscate(employee.Location)
	}
}

func (e *Employees) Deobfuscate() {
	for i, employee := range e.Employee {
		e.Employee[i].Id = obfuscator.Unobfuscate(employee.Id)
		e.Employee[i].FirstName = obfuscator.Unobfuscate(employee.FirstName)
		e.Employee[i].SecondName = obfuscator.Unobfuscate(employee.SecondName)
		e.Employee[i].Location = obfuscator.Unobfuscate(employee.Location)
	}
}
