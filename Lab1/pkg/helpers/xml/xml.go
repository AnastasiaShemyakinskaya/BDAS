package xml

import (
	"encoding/xml"
	"io/ioutil"

	"github.com/lab1/pkg/types"
)

func UnmarshalFileToStruct(filepath string, e *types.Employees) error {
	byteValue, err := ioutil.ReadFile(filepath)
	if err != nil {
		return err
	}
	err = xml.Unmarshal(byteValue, e)
	if err != nil {
		return err
	}
	return nil
}

func MarshalStructToFile(filepath string, e *types.Employees) error {
	bytes, err := xml.MarshalIndent(e, "", "    ")
	if err != nil {
		return err
	}
	err = ioutil.WriteFile(filepath, bytes, 0644)
	if err != nil {
		return err
	}
	return nil
}
