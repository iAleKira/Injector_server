#!/bin/bash
grep "match ftp" injectable-service-probes.txt > toInject/ftp.txt
grep "match ssh" injectable-service-probes.txt > toInject/ssh.txt
#grep "match telnet" injectable-service-probes.txt > toInject/telnet.txt
grep "match smtp" injectable-service-probes.txt > toInject/smtp.txt
grep "match http" injectable-service-probes.txt > toInject/http.txt
#grep "match dns" injectable-service-probes.txt > toInject/dns.txt
#grep "match finger" injectable-service-probes.txt > toInject/finger.txt
#grep "match kerberos" injectable-service-probes.txt > toInject/kerberos.txt
#grep "match nntp" injectable-service-probes.txt > toInject/nntp.txt
#grep "match netbios" injectable-service-probes.txt > toInject/netbios.txt
#grep "match imap" injectable-service-probes.txt > toInject/imap.txt
#grep "match snmp" injectable-service-probes.txt > toInject/snmp.txt
#grep "match ldap" injectable-service-probes.txt > toInject/ldap.txt
#grep "match directconnect" injectable-service-probes.txt > toInject/directconnect.txt
#grep "match microsoft-d" injectable-service-probes.txt > toInject/microsoft-d.txt
#grep "match rtsp" injectable-service-probes.txt > toInject/rtsp.txt
#grep "match nntp" injectable-service-probes.txt > toInject/nntp.txt
#grep "match ipp" injectable-service-probes.txt > toInject/ipp.txt
#grep "match pop3" injectable-service-probes.txt > toInject/pop3.txt