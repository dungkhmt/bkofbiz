
import org.ofbiz.base.util.UtilDateTime;

listShippers = delegator.findByAnd("Shippers",null);

context.key_listShippers =  listShippers; 