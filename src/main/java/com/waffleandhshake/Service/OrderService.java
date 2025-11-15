package com.waffleandhshake.Service;


import com.waffleandhshake.Entity.Order;
import com.waffleandhshake.Repository.OrderRepository;
import com.waffleandhshake.Template.EmailTemplateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepo;

    @Autowired
    private EmailTemplateBuilder emailTemplateBuilder;

    public OrderService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Autowired
    private MailService mailService;

    public Order placeOrder(Order order) {
        order.setOrderTime(LocalDateTime.now());
        order.setStatus("Placerad");
        Order savedOrder = orderRepo.save(order);

        String emailContent = String.format("""
<html>
<head>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #fdf6f0;
            margin: 0;
            padding: 0;
        }
        .container {
            background-color: #fff;
            max-width: 600px;
            margin: 30px auto;
            padding: 20px 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            border-left: 8px solid #d2691e;
        }
        .header {
            text-align: center;
            color: #d2691e;
            font-size: 24px;
            margin-bottom: 20px;
            font-weight: bold;
        }
        .content {
            font-size: 16px;
            color: #333;
            line-height: 1.6;
        }
        .order-info {
            background-color: #f9f1e7;
            padding: 15px;
            border-radius: 8px;
            margin-top: 20px;
            border: 1px dashed #d2691e;
        }
        .footer {
            margin-top: 30px;
            font-size: 14px;
            color: #777;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">☕ Tack för din beställning!</div>
        <div class="content">
            <p>Hej <strong>%s</strong>,</p>
            <p>Vi är glada att bekräfta din beställning på <strong>Albasha Café</strong>!</p>

            <div class="order-info">
                <p><strong>Beställnings-ID:</strong> %d</p>
                <p><strong>Bordnummer:</strong> %s</p>
                <p><strong>Totalt belopp:</strong> %.2f kr</p>
                <p><strong>Status:</strong> %s</p>
            </div>

            <p>Vi hoppas att du njuter av din måltid och upplevelsen hos oss.</p>
            <p>Tveka inte att kontakta oss om du behöver något!</p>
        </div>

        <div class="footer">
            — Vänliga hälsningar, <br/>
            <strong>Team Albasha</strong> <br/>
            <em>Ditt favoritkafé i kvarteret 🍰</em>
        </div>
    </div>
</body>
</html>
""",
                order.getCustomerName(),
                savedOrder.getId(),
                order.getTableNo(),
                order.getTotalAmount(),
                savedOrder.getStatus()
        );

        // Skicka bekräftelsemail
        mailService.sendOrderConfirmation(order.getEmail(), "Beställningsbekräftelse", emailContent);

        return savedOrder;
    }

    public Order updateOrderStatus(Long id, String newStatus) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(newStatus);
        Order saved = orderRepo.save(order);

        if ("REDO".equalsIgnoreCase(newStatus)) {
            String content = emailTemplateBuilder.buildOrderReadyHtml(order);
            mailService.sendOrderReady(order.getEmail(), "Din beställning är klar! 🍽️", content);
        }

        return saved;
    }



    public List<Order> allOrders() {
        return orderRepo.findAll();
    }
}
