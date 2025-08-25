package com.example.database.Controller;

import com.example.database.entity.Account;
import com.example.database.Accounts_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class AccountsController {

    @Autowired
    private Accounts_services accountService;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("account", new Account());
        return "bank"; // This should match your HTML template name
    }

    // Add this to properly handle favicon requests
    @GetMapping("favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {
        // Intentionally empty - prevents the favicon request from being processed by other controllers
    }

    @PostMapping("/accounts")
    public String createAccount(@ModelAttribute Account account, RedirectAttributes redirectAttributes) {
        try {
            Account savedAccount = accountService.insertAccount(account);
            redirectAttributes.addFlashAttribute("message", 
                "Account created successfully! Account Number: " + savedAccount.getAccount_number());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/accounts/details")
    public String getAccountDetails(@RequestParam(required = false) Long accountNumber, Model model, 
                                   RedirectAttributes redirectAttributes) {
        // Handle case where accountNumber is null (like from favicon.ico)
        if (accountNumber == null) {
            redirectAttributes.addFlashAttribute("error", "Please enter an account number");
            return "redirect:/";
        }
        
        try {
            Account account = accountService.getAccountDetails(accountNumber);
            if (account != null) {
                model.addAttribute("accountDetails", account);
            } else {
                redirectAttributes.addFlashAttribute("error", "Account not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error retrieving account details: " + e.getMessage());
        }
        model.addAttribute("account", new Account());
        return "bank"; // This should match your HTML template name
    }

    @PostMapping("/accounts/deposit")
    public String deposit(@RequestParam Long accountNumber, @RequestParam Double amount,
                         RedirectAttributes redirectAttributes) {
        try {
            Account account = accountService.deposit(accountNumber, amount);
            redirectAttributes.addFlashAttribute("message", 
                "Successfully deposited $" + amount + " to account " + accountNumber);
            redirectAttributes.addAttribute("accountNumber", accountNumber);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accounts/details";
    }

    @PostMapping("/accounts/withdraw")
    public String withdraw(@RequestParam Long accountNumber, @RequestParam Double amount,
                          RedirectAttributes redirectAttributes) {
        try {
            Account account = accountService.withdraw(accountNumber, amount);
            redirectAttributes.addFlashAttribute("message", 
                "Successfully withdrew $" + amount + " from account " + accountNumber);
            redirectAttributes.addAttribute("accountNumber", accountNumber);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accounts/details";
    }

    @PostMapping("/accounts/close")
    public String closeAccount(@RequestParam Long accountNumber, RedirectAttributes redirectAttributes) {
        try {
            Account account = accountService.closeAccount(accountNumber);
            redirectAttributes.addFlashAttribute("message", 
                "Account " + accountNumber + " has been closed successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error closing account: " + e.getMessage());
        }
        return "redirect:/";
    }
}