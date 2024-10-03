//package cleancode.minesweeper.tobe;
//
///**
// * packageName    : cleancode.minesweeper.tobe
// * fileName       : Refactor
// * author         : doungukkim
// * date           : 2024. 10. 3.
// * description    :
// * ===========================================================
// * DATE              AUTHOR             NOTE
// * -----------------------------------------------------------
// * 2024. 10. 3.        doungukkim       최초 생성
// */
//public class Refactor {
//
//    public boolean validateOrder2(Order order) {
//        if (order.getItems().size() == 0) {
//            log.info("주문 항목이 없습니다.");
//            return false;
//        } else {
//            if (order.getTotalPrice() > 0) {
//                if (!order.hasCustomerInfo()) {
//                    log.info("사용자 정보가 없습니다.");
//                    return false;
//                } else {
//                    return true;
//                }
//            } else if (!(order.getTotalPrice() > 0)) {
//                log.info("올바르지 않은 총 가격입니다.");
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public boolean validateOrder(Order order) {
//        // 주문이 없으면 false
//        if (order.isEmpty()) {
//            log.info("주문 항목이 없습니다.");
//            return false;
//        }
//
//        // 가격이 0보다 크지 않으면 false
//        if (order.ofTotalPriceNotBiggerThan(0)) {
//            log.info("올바르지 않은 총 가격입니다.");
//            return false;
//        }
//
//        // 주문자 정보가 없으면 false
//        if (order.doesNotHasCustomerInfo()) {
//            log.info("사용자 정보가 없습니다.");
//            return false;
//        }
//        return true;
//    }
//}
//
//
//
//public class Order() {
//    public int getItems() {
//
//    }
//}
