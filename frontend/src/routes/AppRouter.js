import { BrowserRouter, Route, Routes } from 'react-router-dom';

import { Admin, NotFound, Store } from '~/layouts';
import { adminRoutes, privateRoutes, publicRoutes } from '~/routes/routes';

import {
  Cart,
  Collection,
  Home,
  ProductDetail,
  SearchResult,
  Login,
  Register,
  RecoverPassword,
  Checkout,
  CheckoutSuccess,
  Account,
} from '~/pages';
import { ChangePassword, Order, OrderDetail, UserInfo } from '~/components';
import {
  Dashboard,
  ManageCategory,
  ManageColor,
  ManageProduct,
  ManageSize,
  ManageUser,
  MyAccount,
} from '~/layouts/Admin/page';

import ClientPrivateRoute from './ClientPrivateRoute';
import AdminPrivateRoute from './AdminPrivateRoute';
import LoginRegisterProtectRoute from './LoginRegisterProtectRoute';
import Wrapper from './Wrapper';
import AuthRoute from './AuthRoute';

const AppRouter = () => {
  return (
    <BrowserRouter>
      <Wrapper>
        <Routes>
          {/* Store */}
          <Route
            path={publicRoutes.home}
            element={
              <ClientPrivateRoute>
                <Store />
              </ClientPrivateRoute>
            }
          >
            <Route index element={<Home />} />
            <Route path={`${publicRoutes.collection}/:type`} element={<Collection />} />
            <Route path={`${publicRoutes.collection}/:type/:productname`} element={<ProductDetail />} />
            <Route path={`${publicRoutes.cart}`} element={<Cart />} />
            <Route path={publicRoutes.search} element={<SearchResult />} />
            <Route
              path={privateRoutes.account}
              element={
                <AuthRoute>
                  <Account />
                </AuthRoute>
              }
            >
              <Route index element={<UserInfo />} />
              <Route path={privateRoutes.order} element={<Order />} />
              <Route path={privateRoutes.orderDetail} element={<OrderDetail />} />
              <Route path={privateRoutes.changePassword} element={<ChangePassword />} />
            </Route>
            {/* Sign in/Sign up */}
            <Route
              path={privateRoutes.login}
              element={
                <LoginRegisterProtectRoute>
                  <Login />
                </LoginRegisterProtectRoute>
              }
            />
            <Route
              path={privateRoutes.register}
              element={
                <LoginRegisterProtectRoute>
                  <Register />
                </LoginRegisterProtectRoute>
              }
            />
          </Route>
          <Route
            path={privateRoutes.recoverPassword}
            element={
              <LoginRegisterProtectRoute>
                <RecoverPassword />
              </LoginRegisterProtectRoute>
            }
          />

          {/* Checkout */}
          <Route
            path={publicRoutes.checkout}
            element={
              <ClientPrivateRoute>
                <AuthRoute>
                  <Checkout />
                </AuthRoute>
              </ClientPrivateRoute>
            }
          />
          <Route
            path={`${publicRoutes.checkoutSuccess}/:orderCode`}
            element={
              <ClientPrivateRoute>
                <AuthRoute>
                  <CheckoutSuccess />
                </AuthRoute>
              </ClientPrivateRoute>
            }
          />

          {/* Admin */}
          <Route
            path={adminRoutes.default}
            element={
              <AdminPrivateRoute>
                <AuthRoute>
                  <Admin />
                </AuthRoute>
              </AdminPrivateRoute>
            }
          >
            <Route index element={<Dashboard />} />
            <Route index path={adminRoutes.dashboard} element={<Dashboard />} />
            <Route index path={adminRoutes.manageCategory} element={<ManageCategory />} />
            <Route index path={adminRoutes.manageSize} element={<ManageSize />} />
            <Route index path={adminRoutes.manageColor} element={<ManageColor />} />
            <Route index path={adminRoutes.manageProduct} element={<ManageProduct />} />
            <Route index path={adminRoutes.manageUser} element={<ManageUser />} />
            <Route index path={adminRoutes.myAccount} element={<MyAccount />} />
          </Route>

          {/* Notfound */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </Wrapper>
    </BrowserRouter>
  );
};

export default AppRouter;
