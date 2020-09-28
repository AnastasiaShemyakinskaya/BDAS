package obfuscator

import (
	"strings"

	"github.com/lab1/pkg/constant"
)

func Unobfuscate(s string) string {
	var result string
	for i := 0; i < len(s); i++ {
		c := string(s[i])
		index := strings.Index(constant.Target, c)
		result += string(constant.Source[index])
	}
	return result
}
