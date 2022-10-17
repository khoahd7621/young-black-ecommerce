import { useParams } from 'react-router-dom';

import DetailBlock from './DetailBlock/DetailBlock';
import ImageBlock from './ImageBlock/ImageBlock';
import Tabs from './Tabs/Tabs';
import { BreadCrumb } from '~/components';
import { Nested } from '~/components/BreadCrumb/BreadCrumb';

import { publicRoutes } from '~/routes/routes';

const ProductDetail = () => {
  const { type, productname } = useParams();

  return (
    <div className={'product-detail'}>
      <div className="container mx-auto max-w-[730px] lg:max-w-[970px] xl:max-w-[1150px] px-3">
        <BreadCrumb current={productname}>
          <Nested path={`${publicRoutes.collection}/${type}`} name={type} />
        </BreadCrumb>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-2">
          <ImageBlock />
          <DetailBlock />
        </div>
        <div className="product-tab">
          <Tabs />
        </div>
        <div className="grid grid-cols-1">
          <div className="related-product"></div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail;