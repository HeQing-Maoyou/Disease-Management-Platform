<#
快速检查本机 Vite 服务和常用地址连通性脚本。
用法:
    cd "E:\Disease Management Platform"
    .\scripts\check-connectivity.ps1
#>
$ports = 5173
$ips = (Get-NetIPAddress -AddressFamily IPv4 -ErrorAction SilentlyContinue | Where-Object { $_.IPAddress -notlike '169.*' -and $_.IPAddress -ne '127.0.0.1' }).IPAddress
Write-Output "本机 IPv4 地址:"
$ips | ForEach-Object { Write-Output " - $_" }

foreach ($ip in $ips) {
    $url = "http://$ip:$ports/"
    try {
        $res = Invoke-WebRequest -Uri $url -UseBasicParsing -TimeoutSec 5 -ErrorAction Stop
        Write-Output "[OK] $url -> $($res.StatusCode) $($res.StatusDescription)"
    } catch {
        Write-Output "[ERR] $url -> $_"
    }
}

Write-Output "本机回环地址测试:"
try { Invoke-WebRequest -Uri "http://127.0.0.1:$ports/" -UseBasicParsing -TimeoutSec 5 -ErrorAction Stop | Select-Object StatusCode, StatusDescription | Format-List } catch { Write-Output "127.0.0.1:$ports 访问失败: $_" }
