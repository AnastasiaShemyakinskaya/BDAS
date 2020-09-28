package obfuscator

import (
	"strings"

	"github.com/lab1/pkg/constant"
)

func Obfuscate(s string) string {
	var result string
	for i := 0; i < len(s); i++ {
		c := string(s[i])
		index := strings.Index(constant.Source, c)
		result += string(constant.Target[index])
	}
	return result
}
