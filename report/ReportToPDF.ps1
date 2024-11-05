param (
    [string]$reportFile,
    [string]$outputFile = ( [System.IO.Path]::ChangeExtension($reportFile, ".pdf"))
)

# Sprawdzenie, czy plik raportu istnieje
if (-Not (Test-Path $reportFile))
{
    Write-Host "Plik $reportFile nie istnieje."
    exit 1
}

# Generowanie PDF z podanego pliku Markdown
pandoc.exe $reportFile -o $outputFile --pdf-engine=xelatex -V geometry:a3paper -V geometry:margin=1in -V lang=pl -V pagestyle=empty --highlight-style=tango

# Otwieranie wygenerowanego pliku PDF
Start-Process $outputFile

# SIG # Begin signature block
# MIIIWAYJKoZIhvcNAQcCoIIISTCCCEUCAQExCzAJBgUrDgMCGgUAMGkGCisGAQQB
# gjcCAQSgWzBZMDQGCisGAQQBgjcCAR4wJgIDAQAABBAfzDtgWUsITrck0sYpfvNR
# AgEAAgEAAgEAAgEAAgEAMCEwCQYFKw4DAhoFAAQUCSsMIC2Wol5mReVKLE6FIl9x
# qz+gggT6MIIE9jCCAt6gAwIBAgIQYYPyfUBBC6pE/rAfOslXOzANBgkqhkiG9w0B
# AQsFADATMREwDwYDVQQDDAhha290dSBDQTAeFw0yMjA5MjAxOTQ4MDFaFw0zMjA5
# MjAxOTU4MDFaMBMxETAPBgNVBAMMCGFrb3R1IENBMIICIjANBgkqhkiG9w0BAQEF
# AAOCAg8AMIICCgKCAgEAvGcae/FCZugTbghxO7Qv9wQKvRvp9/WvJyJci/SIsPr1
# /Mf5wfBTJ3aCvyjFvHfcsDH4NdHZubHO531tc1NHCDh+Ztkr5hbOdl3x46nEXm6u
# e4Fiw23SB02dU3dAnFvNSGEE5jhQDOApGX/u7xEW4ZXrvMC5yLCBa3Kva1abPx5b
# owvQlHhiSsn039/K2xSNhR+x4QcgEIo9JYdcob0f7ZY3AhXT+f1PNyYe075SY+t2
# y1YMlPlq4THolVUB4yB5MknAOG7IoxFt0U9vXhMSjbb06LZ/I/2RpAJd/qcaC/aX
# CBvKYQbbmEqMqKutic/Q23cQU2jcuRxyy+Y5QphALwdkQGIuvOOIQCak/ZKa6k5S
# 5U3zcMSbGOFF1BHdLSmcUnicsuvMM4uOT0zF/yzuSv5fSo3t6W5VHa+1Ct8ygt3/
# Byq2dLPskUPn0khR3/PaC8Px0k6TpcL1auKeb/uObvckBH/NVvQebtFuXMFXCayw
# ZFQx2dGfqb20Q5ZDNw5u8PtrSAeTaqZ7shrcsHbi59ztASvNjapdnhosQ26ir5bD
# Urzn7Fm/R/tZ9wpCuZ6i2LErckKGMW0Lk1ku0HJv83q/rr0vkrbEXUWx6eaaXwQj
# IacKX8IvED/HN1gQ9WfkvLmQurF9ZUfJQDC/WNrIwYw4advSARKs/4WE+HmN1g0C
# AwEAAaNGMEQwDgYDVR0PAQH/BAQDAgeAMBMGA1UdJQQMMAoGCCsGAQUFBwMDMB0G
# A1UdDgQWBBSUHb/MW3YJQEoACPnV20ZgngOGCDANBgkqhkiG9w0BAQsFAAOCAgEA
# C6pw+UgUjitD9crDEpEPIcmC/Eiif7DnMI2xG1aS8drSFkTvJdmG1yI4gUigjncb
# LfDSLbUIwAUfaM6V1zPb/ec0dg0Nkn+Za1fpuIXxuPKtvrqr9FLfc70D3AphNrDD
# rFEd3c1ykLed7lllMYaLXkfWDRlxhhpP+LR9qbgvTxFbWk/7yA7kJrwEaDgfqqME
# QEE9xZDEIN/f1ycTnh0qmUwYoHDEKbOet/OgiILjzqIjplnaaKJIzFjmfDDK8JY+
# 0tl3hnyFHkPVe9sKTIEVhjc8XlaaCDDTEPTiWvB3TPMLZCqcwqQ4WdcWpS0Dp1Ms
# XvRVv8NkcDMPzFpgqFpkkrkqt94IESUycaAQe+czlurf/KiQjzAjVvhZFspqbBi8
# 83AZ9+mBQhtQqgzcZYSF2LAPbfTXCPw8daT/hOrUaU72YrA4ON64ZRYvcaj9u1AN
# +pxo8TY+YNak+tVByU3sfLfFwbJMJi63be1yo1yLc3b/d3DrJz3AIY82LrtdQcT3
# tj3QnyvVHpFvtzKZxO5hSgaTksmRBYJZ6cYcBgW69l8UpppiyAtzKo4AvD1XXlc6
# ehYjdBVms5F9spAWjwzXg9lWQSsul7V6WB7/PIaTF4hsZ9IylRl4FnBwcJbTdjXi
# E8oA77fIHMj6jOyxEeP6WGzjDYxBnLKyV/lVqk7WkqkxggLIMIICxAIBATAnMBMx
# ETAPBgNVBAMMCGFrb3R1IENBAhBhg/J9QEELqkT+sB86yVc7MAkGBSsOAwIaBQCg
# eDAYBgorBgEEAYI3AgEMMQowCKACgAChAoAAMBkGCSqGSIb3DQEJAzEMBgorBgEE
# AYI3AgEEMBwGCisGAQQBgjcCAQsxDjAMBgorBgEEAYI3AgEVMCMGCSqGSIb3DQEJ
# BDEWBBRLbv83VY2U17jXOCwJBl9Ca3bKkTANBgkqhkiG9w0BAQEFAASCAgCgQiVg
# SSDjL7bITpTwH4bqjqLd/KMB7Hq3yULcEsaZ++7kQouRON9k5vQvNudIEtz690aR
# eIczEZ9oDoEV7qvjHI9dk2Jl4rf8mHgawpr05lr85QLsiEO2slLEL+RUzl+wjQLr
# Ory5QwRN4BMFloc5p+lMMffsTI/91UkVocQXMyjyfeNB9+QVMv5TocsGyQZFBreG
# yckKz/MEwEWyoQyfmAcftqOPZDfs0imyYKXxCfkGrTEyeUhZO8jVhHeBC7cZi1Vc
# 79BqVZfM84Y8J4pNs03bIRc1AaVY1BNQP5tbVzdtGtupVxmajARQZn8B1vkQWsjG
# Qip2rvKo2ZmWL55WXvtcogIg9JmM54wiZus1Oliuu/AaQrgzrSyZ0MrWuDgo7kgd
# 96jjC7G7bZuy9sKp9Ic7CX7hhm51OBwfAhlZnX0/Te1U8e30aXxxK/Tn+TwxwDgK
# 3iFz1gkjLNdTwNDbJOCJY5P/H3R2ygwi9vuq/mWMFYJN3n89FKbioHWA1jQAV/Qi
# zYMqW9vDOHobth7lt1t3074cISqi8LR6kgapM65zp00s2CFet+lTMSDBpw7nbbv8
# /0z57/g2OqsOlW05WS/W/JlocYZMZ7t15Gttt18Y5BFhwzrlfFLDRDwLX0AJbRn1
# 729EP177t42BxwdYXpiMU6XTKbYC8FQbODyfGA==
# SIG # End signature block
