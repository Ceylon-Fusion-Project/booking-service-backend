package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.repo.BookingCartItemRepo;
import com.ceylone_fusion.booking_service.repo.BookingCartRepo;
import com.ceylone_fusion.booking_service.dto.request.BookingCartItemRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.BookingCartRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.RemoveBookingCartItemRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.BookingCartResponseDTO;
import com.ceylone_fusion.booking_service.entity.BookingCart;
import com.ceylone_fusion.booking_service.entity.BookingCartItem;
import com.ceylone_fusion.booking_service.service.BookingCartService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class BookingCartServiceIMPL implements BookingCartService {

    @Autowired
    private BookingCartRepo bookingCartRepo;

    @Autowired
    private BookingCartItemRepo bookingCartItemRepo;

    @Autowired
    private ModelMapper modelMapper;

//    @Override
//    @Transactional
//    public BookingCartResponseDTO addToCart(BookingCartRequestDTO bookingCartRequestDTO) {
//        validateBookingCartRequest(bookingCartRequestDTO);
//
//        BookingCart bookingCart = bookingCartRepo.findByUserId(bookingCartRequestDTO.getUserId())
//                .orElseGet(() -> createNewCart(bookingCartRequestDTO.getUserId()));
//
//        if (bookingCart.getBookingCartItems() == null) {
//            bookingCart.setBookingCartItems(new ArrayList<>());
//        }
//
//        BookingCartItemRequestDTO bookingCartItemRequestDTO = bookingCartRequestDTO.getBookingCartItem();
//        processCartItem(bookingCart, bookingCartItemRequestDTO);
//
//        BookingCart savedCart = bookingCartRepo.save(bookingCart);
//        return modelMapper.map(savedCart, BookingCartResponseDTO.class);
//    }

//    @Override
//    @Transactional
//    public BookingCartResponseDTO removeCartItem(RemoveBookingCartItemRequestDTO removeBookingCartItemRequestDTO) {
//        Long userId = removeBookingCartItemRequestDTO.getUserId();
//        Long productId = removeBookingCartItemRequestDTO.getProductId();
//
//        BookingCart cart = bookingCartRepo.findByUserId(userId)
//                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user ID: " + userId));
//
//        if (cart.getBookingCartItems() == null || cart.getBookingCartItems().isEmpty()) {
//            throw new IllegalArgumentException("The cart is empty. Cannot remove product ID: " + productId);
//        }
//
//        Optional<BookingCartItem> itemToRemove = cart.getBookingCartItems().stream()
//                .filter(item -> item.getProductId().equals(productId))
//                .findFirst();
//
//        if (itemToRemove.isPresent()) {
//            cart.getBookingCartItems().remove(itemToRemove.get());
//            bookingCartItemRepo.delete(itemToRemove.get());
//            BookingCart updatedCart = bookingCartRepo.save(cart);
//            return modelMapper.map(updatedCart, BookingCartResponseDTO.class);
//        } else {
//            throw new IllegalArgumentException("Product ID: " + productId + " not found in the cart.");
//        }
//    }

//    @Override
//    @Transactional
//    public BookingCartResponseDTO getCartItemsByUserId(Long userId) {
//        if (userId == null) {
//            throw new IllegalArgumentException("User ID must not be null.");
//        }
//
//        BookingCart bookingCart = bookingCartRepo.findByUserId(userId)
//                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user ID: " + userId));
//
//        if (bookingCart.getBookingCartItems() == null || bookingCart.getBookingCartItems().isEmpty()) {
//            bookingCart.setBookingCartItems(new ArrayList<>());
//        }
//
//        return modelMapper.map(bookingCart, BookingCartResponseDTO.class);
//    }

//    private void validateBookingCartRequest(BookingCartRequestDTO bookingCartRequestDTO) {
//        if (bookingCartRequestDTO.getUserId() == null || bookingCartRequestDTO.getBookingCartItem() == null) {
//            throw new IllegalArgumentException("User ID and cart item must not be null.");
//        }
//
//        BookingCartItemRequestDTO bookingCartItemRequestDTO = bookingCartRequestDTO.getBookingCartItem();
//        if (bookingCartItemRequestDTO.getProductId() == null || bookingCartItemRequestDTO.getBookingCartItemQuantity() <= 0) {
//            throw new IllegalArgumentException("Cart item must have a valid product ID and a quantity greater than zero.");
//        }
//    }

//    private BookingCart createNewCart(Long userId) {
//        BookingCart bookingCart = new BookingCart();
//        bookingCart.setUserId(userId);
//        bookingCart.setBookingCartItems(new ArrayList<>());
//        return bookingCartRepo.save(bookingCart);
//    }

//    private void processCartItem(BookingCart bookingCart, BookingCartItemRequestDTO bookingCartItemRequestDTO) {
//        Long productId = bookingCartItemRequestDTO.getProductId();
//        Integer quantity = bookingCartItemRequestDTO.getBookingCartItemQuantity();
//        Double price = bookingCartItemRequestDTO.getBookingCartItemPrice();
//
//        Optional<BookingCartItem> existingItem = bookingCart.getBookingCartItems().stream()
//                .filter(item -> item.getProductId().equals(productId))
//                .findFirst();
//
//        if (existingItem.isPresent()) {
//            BookingCartItem bookingCartItem = existingItem.get();
//            bookingCartItem.setBookingCartItemQuantity(bookingCartItem.getBookingCartItemQuantity() + quantity);
//        } else {
//            BookingCartItem newItem = new BookingCartItem();
//            newItem.setProductId(productId);
//            newItem.setBookingCartItemPrice(price);
//            newItem.setBookingCartItemQuantity(quantity);
//            newItem.setBookingCart(cart);
//            bookingCart.getBookingCartItems().add(newItem);
//        }
//    }
}
