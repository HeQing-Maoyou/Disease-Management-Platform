<#
以管理员身份运行此脚本以创建允许 Vite 端口 5173 的防火墙入站规则。
使用方法（以管理员 PowerShell 运行）:
    cd "E:\Disease Management Platform"
    .\scripts\add-firewall-rule.ps1
#>
try {
    New-NetFirewallRule -DisplayName "Vite 5173 (Allow)" -Direction Inbound -Action Allow -Protocol TCP -LocalPort 5173 -Profile Any -ErrorAction Stop
    Write-Output "防火墙规则已创建: Vite 5173 (Allow)"
} catch {
    Write-Error "创建防火墙规则失败: $_"
}
