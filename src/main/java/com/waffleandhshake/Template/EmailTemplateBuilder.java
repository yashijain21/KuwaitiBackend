package com.waffleandhshake.Template;


import com.waffleandhshake.Entity.Order;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateBuilder {

    public String buildOrderReadyHtml(Order order) {
        return String.format("""
<html>
  <head>
    <style>
      body {
        font-family: 'Segoe UI', sans-serif;
        background-color: #fff8f1;
        color: #4a3c30;
        padding: 30px;
        line-height: 1.6;
      }
      .header {
        background-color: #8b5a2b;
        color: white;
        padding: 20px;
        border-radius: 8px 8px 0 0;
        text-align: center;
        font-size: 24px;
        font-weight: bold;
      }
      .content {
        background-color: #ffffff;
        padding: 20px;
        border-radius: 0 0 8px 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
      }
      .highlight {
        color: green;
        font-weight: bold;
      }
      .footer {
        margin-top: 30px;
        font-size: 14px;
        color: #999;
        text-align: center;
      }
    </style>
  </head>
  <body>
    <div class="header">
      Albasha Café – Beställning Klar ☕
    </div>
    <div class="content">
      <p>Hej <strong>%s</strong>,</p>
      <p>Din beställning <strong>#%d</strong> är nu <span class="highlight">KLAR</span>!</p>
      <p>Vänligen hämta din beställning.</p>
      <p>Vi uppskattar ditt besök. Smaklig måltid! 🍽️</p>
      <p style="margin-top: 20px;">Vänliga hälsningar,<br><strong>Team Albasha</strong></p>
    </div>
    <div class="footer">
      Albasha Café • Kaffegatan 123 • Öppet dagligen 07:00–21:00
    </div>
  </body>
</html>
""",
                order.getCustomerName(),
                order.getId(),
                order.getTableNo());
    }

}
